package com.swein.shjetpackcompose.googletutorial.iv_basic.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.application.ui.theme.SHJetpackComposeTheme

object GTBasicView {

    private const val TAG = "GTBasicView"

    @Composable
    fun Content() {
//        Greeting(name = "Coding with cat")
//        Greetings()
//        Greetings(listOf("Coding", "with", "cat"))
        FilledContent(listOf("Coding", "with", "cat"))
    }

    @Composable
    fun FilledContent(list: List<String> = emptyList()) {

        Surface(color = Color.Yellow) {

            Column(modifier = Modifier.fillMaxHeight()) {

                Column(modifier = Modifier.weight(1f)) {

                    if (list.isEmpty()) {
                        Greeting("Android")
                        Divider(color = Color.Black)
                        Greeting("there")
                    }
                    else {
                        for (item in list) {
                            Greeting(item)
                            Divider(color = Color.Black)
                        }
                    }

                }

                CounterWithStateHoisting()
            }

        }

    }

    @Composable
    fun Greetings(list: List<String> = emptyList()) {
        Surface(color = Color.Yellow) {

            Column {

                if (list.isEmpty()) {
                    Greeting("Android")
                    Divider(color = Color.Black)
                    Greeting("there")
                }
                else {
                    for (item in list) {
                        Greeting(item)
                        Divider(color = Color.Black)
                    }
                }

                Divider(color = Color.Transparent, thickness = 32.dp)

//                CounterWithInnerState()
                CounterWithStateHoisting()
            }
        }
    }

    @Composable
    fun CounterWithStateHoisting() {

        val count = remember {
            mutableStateOf(0)
        }

//        CounterOne(count.value) {
//            count.value++
//        }

        CounterTwo(count.value) { value ->
            count.value = value
        }
    }
    @Composable
    fun CounterOne(value: Int, onButtonClick: () -> Unit) {
        Button(onClick = onButtonClick) {
            ILog.debug(TAG, "recompose $value")
            Text("I've been clicked $value times")
        }
    }
    @Composable
    fun CounterTwo(value: Int, onUpdateValue: (Int) -> Unit) {
        Button(onClick = {
            onUpdateValue(value + 1)
        }) {
            ILog.debug(TAG, "recompose $value")
            Text("I've been clicked $value times")
        }
    }

    @Composable
    fun CounterWithInnerState() {

        val count = remember {
            mutableStateOf(0)
        }

        // 状态提升是使内部状态可由调用它的函数控制的方法
        // 状态可提升可避免重复状态和引入错误，有助于重用可组合物，并使可组合物更易于测试。
        // 但是，可组合调用者不感兴趣的状态应该是内部的，不需要进行提升。
        Button(onClick = {
            count.value++
            ILog.debug(TAG, count.value)
        }) {

            // this scope will recompose when you don't use the value of count
            ILog.debug(TAG, "recompose ${count.value}")
            Text("I've been clicked ${count.value} times")

            // this scope will not recompose when you don't use the value of count
//            ILog.debug(TAG, "recompose??")
//            Text("I've been clicked")
        }
    }

    @Composable
    fun Greeting(name: String) {
        Surface(color = Color.Yellow) {
            Text (text = "Hello $name", modifier = Modifier.padding(24.dp))
        }
    }
}

@Preview(showBackground = true, name = "Text preview")
@Composable
fun DefaultPreview() {
    GTBasicView.Content()
}