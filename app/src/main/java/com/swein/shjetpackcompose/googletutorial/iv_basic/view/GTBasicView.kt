package com.swein.shjetpackcompose.googletutorial.iv_basic.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
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
        
        SHJetpackComposeTheme {

//            SingleTextExample()
//            TextColumnExample()
//            TextColumnWithListExample(listOf("Coding", "with", "cat"))
//            FullScreenColumnWithCounterExample(listOf("Coding", "with", "cat"))

//            val count = remember {
//                mutableStateOf(0)
//            }
//            ButtonWithStateHoistingExample(count.value) {
//                count.value++
//            }

            FullScreenLazyColumnWithCounterExample()
        }
    }

    @Composable
    fun FullScreenLazyColumnWithCounterExample(list: List<String> = List(1000) { "item index $it" }, modifier: Modifier = Modifier) {

        Surface(color = Color.Cyan) {

            Column(modifier = modifier.fillMaxHeight()) {

                LazyColumn(modifier = modifier.weight(1f)) {

                    items(items = list) { item ->

                        Text(text = "item index $item", modifier = modifier.padding(16.dp))
                        Divider(color = Color.LightGray)
                    }

                }

                val count = remember {
                    mutableStateOf(0)
                }
                Button(onClick = {
                    count.value++
                }) {
                    Text(text = "Button ${count.value}")
                }

            }

        }
    }

    @Composable
    fun ButtonWithStateHoistingExample(value: Int, onButtonClick: () -> Unit) {

        Surface(color = Color.Yellow) {
            Button(onClick = onButtonClick, modifier = Modifier.padding(16.dp)) {
                Text(text = "Button $value")
            }
        }

    }

    @Composable
    fun FullScreenColumnWithCounterExample(list: List<String>, modifier: Modifier = Modifier) {

        val count = remember {
            mutableStateOf(0)
        }

        Surface(color = Color.Cyan) {
            
            Column(modifier = modifier.fillMaxHeight()) {

                Column(modifier = modifier.weight(1f)) {

                    for (item in list) {
                        Text(text = item, modifier = modifier.padding(16.dp))
                        Divider(color = Color.DarkGray)
                    }

                }

                Button(
                    onClick = {
                        count.value++
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (count.value > 5) Color.Green else Color.White
                    )
                ) {
                    Text(text = "click ${count.value} times")
                }
            }
            
        }
    }

    @Composable
    fun TextColumnWithListExample(list: List<String>, modifier: Modifier = Modifier) {

        Surface(color = Color.Yellow) {
            Column {
                for (item in list) {
                    Text(text = item, modifier = modifier.padding(16.dp))
                    Divider(color = Color.DarkGray)
                }
            }
        }
    }

    @Composable
    fun TextColumnExample() {

        Surface(color = Color.Yellow) {
            Column {
                Text(text = "Coding", modifier = Modifier.padding(16.dp))
                Divider(color = Color.LightGray)
                Text(text = "with", modifier = Modifier.padding(16.dp))
                Divider(color = Color.LightGray)
                Text(text = "cat", modifier = Modifier.padding(16.dp))
                Divider(color = Color.LightGray)
                SingleTextExample()
            }
        }

    }

    @Composable
    fun SingleTextExample() {

        Surface(color = Color.Yellow) {
            Text (text = "Hello, Coding with cat", modifier = Modifier.padding(24.dp))
        }

    }



    @Composable
    fun GoogleExample() {
//        Greeting(name = "Coding with cat")
//        Greetings()
//        Greetings(listOf("Coding", "with", "cat"))
//        FilledContent(listOf("Coding", "with", "cat"))
//        FilledContent(List(1000) { "Hello Android #$it" })
    }

    @Composable
    fun NameList(list: List<String>, modifier: Modifier = Modifier) {
        LazyColumn(modifier = modifier) {
            items(items = list) { item ->
                Greeting(name = item)
                Divider(color = Color.Black)
            }
        }
    }

    @Composable
    fun FilledContent(list: List<String> = emptyList()) {

        Surface(color = Color.Yellow) {

            Column(modifier = Modifier.fillMaxHeight()) {

                if (list.size > 3) {
                    NameList(list = list, Modifier.weight(1f))
                }
                else {
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
    fun CounterOne(value: Int, onButtonClick: () -> Unit) =
        Button(
            onClick = onButtonClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (value > 5) Color.Green else Color.White
            )
        ) {
            ILog.debug(TAG, "recompose $value")
            Text("I've been clicked $value times")
        }

    @Composable
    fun CounterTwo(value: Int, onUpdateValue: (Int) -> Unit) =
        Button(
            onClick = {
                onUpdateValue(value + 1)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (value > 5) Color.Green else Color.White
            )
        ) {
            ILog.debug(TAG, "recompose $value")
            Text("I've been clicked $value times")
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
    fun Greeting(name: String) =
        Surface(color = Color.Yellow) {
            Text (text = "Hello $name", modifier = Modifier.padding(24.dp))
        }

}

@Preview(showBackground = true, name = "Text preview")
@Composable
private fun DefaultPreview() {
    GTBasicView.Content()
}