package com.swein.shjetpackcompose.basic.xmltocompose

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

class XMLToComposeExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ContentView()

        }
    }

    @Composable
    private fun ContentView() {

        val state = remember {
            mutableStateOf(0)
        }

        Column(
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        ) {

            //widget.Button
            AndroidView(
                // factory接收一个Context参数, 用来构建一个View.
                factory = { ctx ->
                    //Here you can construct your View
                    android.widget.Button(ctx).apply {
                        text = "My Button"
                        layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        setOnClickListener {
                            state.value++
                        }
                    }
                },
                modifier = Modifier.padding(8.dp)
            )

            //widget.TextView
            AndroidView(
                factory = { ctx ->
                //Here you can construct your View
                    TextView(ctx).apply {
                        layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    }
                },
                // update方法是一个callback, inflate之后会执行, 读取的状态state值变化后也会被执行.
                update = {
                    it.text = "You have clicked the buttons: " + state.value.toString() + " times"
                }
            )

        }

    }
}