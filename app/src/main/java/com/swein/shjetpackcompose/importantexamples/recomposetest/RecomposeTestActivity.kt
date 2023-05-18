package com.swein.shjetpackcompose.importantexamples.recomposetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.swein.framework.utility.debug.ILog

class RecomposeTestActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val testString = mutableStateOf("Coding with")

        setContent {

            // 例子1: scope 1, 2 都会被重组，有性能风险
            ILog.debug("???", "scope 1")

            Column {

                ILog.debug("???", "scope 2")
                Heavy(text = testString.value)
                Text(
                    modifier = Modifier
                        .clickable {
                                   testString.value = "Coding with cat"
                        },
                    text = testString.value
                )

            }
            // 例子1: scope 1, 2 都会被重组，有性能风险

        }
    }
}

@Composable
private fun Heavy() {
    // 内容不变，不会被重组
    Text(text = "Heavy")
}

@Composable
private fun Heavy(text: String) {
    ILog.debug("???", "scope Heavy")
    Text(text = "Heavy $text")
}