package com.swein.shjetpackcompose.basic.webviewexample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

class WebViewAndBottomNavigationBarExampleActivity : AppCompatActivity() {

//    private var webViewHolder: WebViewHolder? = null
    private var webViewHolder: SimpleWebViewHolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ContentView(webViewHolder = webViewHolder)

        }

        if (webViewHolder == null) {
            webViewHolder = SimpleWebViewHolder(this).apply {
                loadUrl("https://www.google.com")
            }
        }
    }
}

@Composable
private fun ContentView(webViewHolder: SimpleWebViewHolder?) {

    val index = remember {
        mutableIntStateOf(0)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                when (index.intValue) {

                    0 -> {
                        Surface(
                            color = Color.White
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("home", color = Color.Black, fontSize = 30.sp)
                            }
                        }
                    }

                    1 -> {
                        webViewHolder?.let {

                            AndroidView(
                                modifier = Modifier
                                    .fillMaxSize(),
                                factory = { context ->
//                                    it.webView

                                    SimpleWebViewHolder(context).apply {
//                                        loadUrl("https://www.google.com")
                                        loadUrl("https://wala-land.com/")
                                    }.webView
                                },
                                update = { view ->

                                }
                            )
                        }
                    }

                    2 -> {
                        Surface(
                            color = Color.White
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("profile", color = Color.Black, fontSize = 30.sp)
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(6.dp)
            ) {
                BottomNavigationBarView(
                    index = index.intValue,
                    onSelect = { newIndex ->
                        index.intValue = newIndex
                    }
                )
            }
        }
    }
}

@Composable
private fun BottomNavigationBarView(
    index: Int,
    onSelect: (newIndex: Int) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {

        BottomNavigationBarItemView(
            modifier = Modifier.weight(1f),
            stringResource = "home",
            focus = index == 0,
            onSelect = {
                onSelect(0)
            }
        )

        BottomNavigationBarItemView(
            modifier = Modifier.weight(1f),
            stringResource = "web view",
            focus = index == 1,
            onSelect = {
                onSelect(1)
            }
        )

        BottomNavigationBarItemView(
            modifier = Modifier.weight(1f),
            stringResource = "profile",
            focus = index == 2,
            onSelect = {
                onSelect(2)
            }
        )
    }

}

@Composable
private fun BottomNavigationBarItemView(
    modifier: Modifier,
    stringResource: String,
    focus: Boolean,
    onSelect: () -> Unit
) {

    Column(
        modifier = modifier
            .wrapContentHeight()
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = LocalIndication.current,
                onClick = {
                    onSelect()
                }
            )
            .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(
            text = stringResource,
            color = if (focus) { Color.Black } else { Color.LightGray },
            fontSize = 15.sp,
        )
    }

}