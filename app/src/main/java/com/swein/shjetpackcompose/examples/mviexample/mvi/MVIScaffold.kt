package com.swein.shjetpackcompose.examples.mviexample.mvi

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

interface State
interface Action

interface Reducer<S: State, A: Action> {
    fun reduce(state: S, action: A): S
}

open class MVIViewModel<S: State, A: Action>(
    private val reducer: Reducer<S, A>,
    stateInit: S,
) : ViewModel() {

    private val actions = MutableSharedFlow<A>(extraBufferCapacity = 128)

    var state: S by mutableStateOf(stateInit)
        private set // can only be set in view model

    init {

        viewModelScope.launch {
            actions.collect { action ->
                state = reducer.reduce(state, action)
            }
        }
    }

    fun emit(action: A) {
        val success = actions.tryEmit(action)
        if (success) {
            Log.d("MVIViewModel", "success $action") // log error
        }
        else {
//            error("MVI action buffer overflow") // throw error
            Log.d("MVIViewModel", "MVI action buffer overflow") // log error
        }
    }
}