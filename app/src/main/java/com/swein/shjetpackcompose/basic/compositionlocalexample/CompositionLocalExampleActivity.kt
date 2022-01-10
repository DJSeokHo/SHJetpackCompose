package com.swein.shjetpackcompose.basic.compositionlocalexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class CompositionLocalExampleActivity : ComponentActivity() {

    /**
     * 即使用 CompositionLocal 来完成 composable 树中的数据共享，并且 CompositionLocal 具有层级，
     * 它可以被限定在某个 composable 作为根节点的子树中，默认向下传递，同时子树中的某个 composable 也可以对该 CompositionLocal 进行覆盖，
     * 然后这个新值就会在这个 composable 中继续向下传递。
     */
    private val localMessage = compositionLocalOf { "Subscribe to who ?" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ContentView()
        }
    }

    @Composable
    private fun ContentView() {

        CompositionLocalProvider(localMessage provides "${localMessage.current} Coding") {
            Coding()
        }
    }

    @Composable
    private fun Coding() {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = localMessage.current)

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            CompositionLocalProvider(localMessage provides "${localMessage.current} With") {
                With()
            }

        }

    }

    @Composable
    private fun With() {
        Text(text = localMessage.current)

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        CompositionLocalProvider(localMessage provides "${localMessage.current} Cat") {
            Cat()
        }
    }

    @Composable
    private fun Cat() {
        Text(text = localMessage.current)
    }
}