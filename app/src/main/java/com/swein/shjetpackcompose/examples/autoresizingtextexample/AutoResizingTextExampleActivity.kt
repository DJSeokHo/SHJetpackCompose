package com.swein.shjetpackcompose.examples.autoresizingtextexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AutoResizingTextExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val short = "Coding with cat"
            val middle = "Coding with cat is a youtube channel."
            val long = "Coding with cat is a youtube channel. You can subscribe this channel to watch android development tutorial step by step."

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        AutoResizingText(
                            modifier = Modifier
                                .width(200.dp)
                                .height(100.dp)
                                .background(Color.Black)
                                .padding(10.dp),
                            text = short,
                            color = Color.White,
                            targetTextSize = 30.sp
                        )

                        Spacer(modifier = Modifier.padding(vertical = 20.dp))

                        AutoResizingText(
                            modifier = Modifier
                                .width(200.dp)
                                .height(100.dp)
                                .background(Color.Black)
                                .padding(10.dp),
                            text = middle,
                            color = Color.White,
                            targetTextSize = 30.sp
                        )

                        Spacer(modifier = Modifier.padding(vertical = 20.dp))

                        AutoResizingText(
                            modifier = Modifier
                                .width(200.dp)
                                .height(100.dp)
                                .background(Color.Black)
                                .padding(10.dp),
                            text = long,
                            color = Color.White,
                            targetTextSize = 30.sp
                        )

                    }

                }

            }

        }
    }
}

@Composable
fun AutoResizingText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    textAlign: TextAlign = TextAlign.Start,
    textStyle: TextStyle = TextStyle.Default,
    targetTextSize: TextUnit = textStyle.fontSize,
    maxLines: Int = Int.MAX_VALUE,
) {
    val textSize = remember { mutableStateOf(targetTextSize) }

    Text(
        modifier = modifier,
        text = text,
        color = color,
        textAlign = textAlign,
        fontSize = textSize.value,
        fontFamily = textStyle.fontFamily,
        fontStyle = textStyle.fontStyle,
        fontWeight = textStyle.fontWeight,
        lineHeight = textStyle.lineHeight,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        onTextLayout = { textLayoutResult ->
            val maxCurrentLineIndex: Int = textLayoutResult.lineCount - 1
            if (textLayoutResult.isLineEllipsized(maxCurrentLineIndex)) {
                textSize.value = textSize.value * 0.9f
            }
        },
    )
}