package com.swein.shjetpackcompose.importantexamples.sideeffecttest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import com.swein.framework.utility.debug.ILog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RememberUpdatedStateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val name = remember {
                mutableStateOf("")
            }

            Column {

                Text(text = name.value)

                Button(onClick = {
                    name.value = "coding with cat"
                }) {
                    Text(text = "Button")
                }

                CustomLaunchedEffect(content = name.value)
            }
        }
    }
}

@Composable
private fun CustomLaunchedEffect(content: String) {
    // 3秒后会打印content，如果3秒内外部的值改变了，这里3秒后打印的是改变之前的值，虽然外面传来的值是被remember包裹的。
    // 怎么才能让这里同步获取外部传来的可改变的值呢
    // 方法1
//    val rememberContent = remember {
//        mutableStateOf(content)
//    }
//    rememberContent.value = content
    // 官方给出了简化的方法2
    val rememberContent = rememberUpdatedState(newValue = content)
    LaunchedEffect(key1 = Unit, block = {
        delay(3000)
//        ILog.debug("???", content)
        ILog.debug("???", rememberContent.value) // 方法1
    })
}

// Disposable 也可以用
//@Composable
//private fun CustomDisposableEffect(content: String) {
//    val rememberContent = rememberUpdatedState(newValue = content)
//    DisposableEffect(key1 = Unit, effect = {
//        subscriber.subscribe(rememberContent) // 订阅
//        onDispose {
//            subscriber.unsubscribe(rememberContent) // 取消订阅
//        }
//    })
//}