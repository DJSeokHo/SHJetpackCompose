package com.swein.shjetpackcompose.importantexamples.composecoroutinetest

import android.annotation.SuppressLint
import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.R
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CoroutineStateActivity : ComponentActivity() {

//    private val positionState: StateFlow<Point> = TODO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {

            // StateFlow: 类似于 LiveData
            // Flow: 类似于 RxJAVA

//             produceState(initialValue = , producer = )
//             produceState(initialValue = , key1 = , producer = )

//            val producedState = produceState(initialValue = Point(0, 0), producer = {
//
//                positionState.collect {
//                    value = it
//                }
//
//            })


            val name = remember {
                mutableStateOf("123")
            }

            val age = remember {
                mutableStateOf(18)
            }

            val flow = snapshotFlow {
                name.value
            }

            // 也可以创建多个值的flow
            val flows = snapshotFlow {
                "${name.value} ${age.value}"
            }

            LaunchedEffect(key1 = Unit, block = {

                flow.collect {
                    // name 的改变会触发这里的打印
                    ILog.debug("???", it)

                    // 用途？比如每当某个remember的值发生变化时
                    // 希望记录这个值并上传服务器，比如追踪用户习惯时
                    // 那么想要收集compose的remember的值改变并自动触发行为时
                    // 就用 snapshotFlow
                }

            })

            LaunchedEffect(key1 = Unit, block = {

                flows.collect {
                    // name 或者 age 的改变会触发这里的打印
                    ILog.debug("!!!", it)
                }

            })

            Column {
                Button(onClick = {
                    name.value = "abc"
                }) {
                    Text("button")
                }

                Button(onClick = {
                    age.value = 36
                }) {
                    Text("button")
                }
            }
        }
    }
}