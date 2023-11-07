package com.swein.shjetpackcompose.examples.mviexampletest.mviscaffold

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

/**
 * 为了防止从字面上混淆Model和Intent的概念，我们在这里给他们分别起别名用于区分。
 * Action代Intent或者图中的events。
 * Model(State)一分为二：UIState一般是一种持久的UI形态，在发生生命周期变化时候需要回放。
 * UIEffect一般是一次性消费UI事件，如弹窗、toast、导航等。
 * 所以我们拆分Model为State和Effect。
 */

interface Action // 用户与ui的交互事件

interface State // ui响应的状态

interface Effect // ui响应的事件


/**
 * UserIntent(Action): action用于描述各种请求State或者Effect的动作，
 * 由View发送ViewModel订阅消费，典型的生产者消费者模式，考虑是一对一的关系我们使用Channel来实现，
 * 有些开发者喜欢直接调用ViewModel方法，如果方法还有返回值，就破坏了数据的单向流动。
 *
 * Model(State): State需要订阅观察者模式给view提供数据，在非Compose中我们可以使用LiveData和StateFlow, 在Compose中我们可以直接使用State
 *
 * Model(Effect): Effect 指Android中的一次性事件，比如toast、navigation、back press、click等等，
 * 由于这些状态都是一次性的消费所以不能使用livedata和StateFlow，
 * 我们可以使用SharedFlow或者Channel，考虑多个Composable中要共享 view model 获取sideEffect，这里使用SharedFlow更方便。
 */
abstract class MVIViewModel<A : Action, S: State, E: Effect>: ViewModel() {

    // ====================== action ===========================
    private val _action = Channel<A>()
    init {
        viewModelScope.launch {
            _action.consumeAsFlow().collect {

                // 很多时候我们需要通过上个state的数据来处理这次数据，所以我们要获取当前状态传递
                onAction(it, _state.value)
            }
        }
    }

    val actor: SendChannel<A> by lazy { _action }
    fun sendAction(action: A) = viewModelScope.launch {
        _action.send(action)
    }
    protected abstract fun onAction(action: A, currentState: S)

    // ====================== state ===========================

    // 继承 MVIViewModel 需要实现 state 默认值
    abstract fun initialState(): S

    // 这是 StateFlow 版本
    // 用于发射一个状态，也就是发射一个State
    private val _state by lazy {
        MutableStateFlow(value = initialState())
    }
    // 在view中用于订阅
    val state: StateFlow<S> by lazy {
        _state.asStateFlow()
    }

    // emitState 函数的 挂起版
    protected suspend fun emitState(state: S) {
        Log.d("???", "emitState $state")
        _state.emit(state)
    }



    // ====================== effect ===========================
    /**
     * effect 事件带来的副作用，通常是一次性事件 例如：弹Toast、导航Fragment等
     */
    private val _effect = MutableSharedFlow<E>()

    // 在view中用于订阅
    val effect: SharedFlow<E> by lazy {
        _effect.asSharedFlow()
    }

    protected suspend fun emitEffect(effect: E) {
        _effect.emit(effect)
    }
}