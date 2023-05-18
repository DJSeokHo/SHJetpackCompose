package com.swein.shjetpackcompose.importantexamples.sideeffecttest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect

class SideEffectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // side effect (副作用) (附带效应)
            // 定义: 函数的调用能不能被它的返回值替代，如果能，那就是没有副作用，否则就有副作用。
            // 那么意义在哪？因为Compose都是组件，函数。所以特别强调函数的纯净性，尽量杜绝副作用。
            // Compose 函数最好只做显示界面的事情，不要参杂其他事情，不要影响函数外部，否则就是有副作用。
            // 为什么要这样？因为不这样的话，会让程序的行为变得不可预测。
            // 比如埋点，统计用户行为就是个副作用，但是这是业务需求，但是和Compose 建议的规则冲突。
            // 怎么办？所以有解决方法，最简单的一个函数就是下面这个:
            SideEffect {
                // 这里面写的代码，会先保存起来，暂停执行，等到界面重组全部完成后。再执行一次。
                // 也就是说界面重组被中断了，这里面写的就不会被执行。这样可以多少确保一下副作用的稳定性。比如埋点统计只需要执行一次。
                // 这个本质是一种回掉机制
            }


            // 1. 举个副作用的例子
            // 假设执行到了XXX，突然中断了，但是YYY, ZZZ会对外面有修改的话，就会导致不一致，这是副作用之一
//            Column {
//                XXX
//                YYY
//                ZZZ
//            }

            var count = 0
            // 再来个例子
            Column {
                val names = arrayOf("aaa", "bbb", "ccc", "ddd")
                for (name in names) {
                    Text(
                        text = name
                    )
                    count++
                }
                // 在重组时无法确定次数，所以这里理论上count是4才对，但是其实不一定。
                // 也就是说Column影响了外部的count，这是一种副作用，不要这样写。
                Text("count is $count")
            }
        }
    }

    // 1-1. 这个函数什么都不做，不会影响外部，肯定没有副作用
    fun a() {

    }

    // 1-2. 这个函数修改内部定义的变量，不会影响外部，肯定没有副作用
    fun b() {
        var v = false
        v = true
    }

    var v = false

    // 1-3. 这个函数修改外部定义的变量，影响外部，有副作用
    fun c() {
        v = true
    }

    // 1-4. 有副作用，因为改变了程序的状态
    fun d() {
        println("打印也有副作用")
    }
}