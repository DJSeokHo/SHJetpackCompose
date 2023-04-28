package com.swein.shjetpackcompose.basic.compositionlocalexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

private val LocalName = compositionLocalOf<String> { error("Empty") }
private val LocalChannel = compositionLocalOf<String> { error("Empty") }
private val StaticLocalType = staticCompositionLocalOf<String> { error("Empty") }

class CompositionLocalExampleActivity : ComponentActivity() {

    private val name = mutableStateOf("Coding with cat")

    /**
     * 即使用 CompositionLocal 来完成 composable 树中的数据共享，并且 CompositionLocal 具有层级，
     * 它可以被限定在某个 composable 作为根节点的子树中，默认向下传递，同时子树中的某个 composable 也可以对该 CompositionLocal 进行覆盖，
     * 然后这个新值就会在这个 composable 中继续向下传递。
     *
     * compositionLocalOf, staticCompositionLocalOf 区别是前者会被监听，改变值时刷新UI，而后者不会被监听
     * name 就是个监听例子，给他提供一个状态变量而不是一个常量，那么变量改变值就会被刷新UI
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // 用于上下文或者主题外观系统，可以提供多个值
            CompositionLocalProvider(
                LocalName provides name.value,
                LocalChannel provides "Youtube"
            ) {
                ContentView {
                    name.value = "cwc"
                }
            }

        }
    }

}

@Composable
private fun ContentView(onClick: () -> Unit) {

    TextFirst(onClick)
}

@Composable
private fun TextFirst(onClick: () -> Unit) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = LocalName.current)

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        CompositionLocalProvider(LocalName provides "${LocalName.current} is useful") {
            TextSecond()
        }

        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        Button(onClick = onClick) {
            Text("Button")
        }
    }

}

@Composable
private fun TextSecond() {
    Text(text = LocalName.current)

    Spacer(modifier = Modifier.padding(vertical = 10.dp))

    CompositionLocalProvider(LocalName provides "${LocalName.current}\nAndroid development tutorial step by step") {
        TextThird()
    }
}

@Composable
private fun TextThird() {
    Text(
        text = LocalName.current,
        textAlign = TextAlign.Center
    )
}