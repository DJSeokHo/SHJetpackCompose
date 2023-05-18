package com.swein.shjetpackcompose.importantexamples.sideeffecttest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.swein.framework.utility.debug.ILog

class DisposableEffectActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // Side Effect 的升级版，增加了离开界面的监听
            // Side Effect 监听界面进入的话，DisposableEffect既可以监听进入，也可以监听离开
            // 可丢弃效应，用完就丢，一次性的
            // DisposableEffect(key1 = , effect = )

            // 举个例子
            val show = remember {
                mutableStateOf(true)
            }

            val textTest = remember {
                mutableStateOf(1)
            }

            val keyTest = remember {
                mutableStateOf(true)
            }

            Column {

                if (show.value) {

                    Text("????")

                    // 这个括号内的东西受 show 所控制，所以这一块就是可丢弃的，所以在这里面加一个DisposableEffect来监听这一块从屏幕上消失的事件。
                    // 每次的 show的改变都会触发这个DisposableEffect，虽然key不变，但是每次这个括号里都是一个新的组件

                    DisposableEffect(key1 = Unit, effect = {

                        ILog.debug("???", "show")

                        onDispose {
                            ILog.debug("???", "hide")
                        }
                    })
                }

                Button(onClick = {
                    show.value = !show.value
                }) {
                    Text(text = "show")
                }


                Button(onClick = {
                    textTest.value++
                }) {
                    Text(text = "count ${textTest.value}")

                    DisposableEffect(key1 = Unit, effect = {

                        // 进入界面啦，只执行一次，就算textTest值改变，Button 重组，
                        // 这里也不会在每次重组时一直反复执行。
                        ILog.debug("???", "count button ok")

                        onDispose {
                            ILog.debug("???", "count button onDispose")
                        }
                    })
                }

                DisposableEffect(key1 = keyTest.value, effect = {
                    // key1 如果被改变的话，DisposableEffect会被重启
                    // 也就是key1 值被改变的话，那么 DisposableEffect会在下一次重组完成后执行
                    // 重启时执行顺序时先执行 onDispose 内部的，再执行 DisposableEffect 内部的
                    // 也就是先打印 key out，再打印 key enter

                    // 那么，问题来了，这个有啥用？可以用于订阅和取消订阅？

                    // 在这里订阅

                    ILog.debug("???", "key enter")

                    onDispose {

                        // 在这里取消订阅
                        ILog.debug("???", "key out")
                    }
                })

                Button(onClick = {
                    keyTest.value = !keyTest.value
                }) {
                    Text(text = "keyTest")
                }
            }
        }
    }
}