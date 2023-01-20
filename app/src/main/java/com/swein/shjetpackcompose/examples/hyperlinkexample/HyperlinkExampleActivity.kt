package com.swein.shjetpackcompose.examples.hyperlinkexample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class HyperlinkExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            ContentView()

        }
    }
}

@Composable
private fun ContentView() {

    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            val spanRangeClickText = buildAnnotatedString {
                append("Hi. I'm your friend Coding with cat.\n")
                append("This is my ")
                pushStringAnnotation(
                    tag = "channel",
                    annotation = "https://www.youtube.com/@codingwithcat/videos"
                )
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                ) {
                    append("Channel.")
                }
                pop()
                append("\n\n And this is ")
                pushStringAnnotation(
                    tag = "snack bar",
                    annotation = "https://www.youtube.com/watch?v=WLQzjtIUVmY"
                )
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                ) {
                    append("a video about custom snack bar. ")
                }
                pop()
                append("I like it.")
            }

            ClickableText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = spanRangeClickText,
                onClick = { offset ->
                    spanRangeClickText.getStringAnnotations(
                        start = offset,
                        end = offset
                    ).firstOrNull()?.let { annotation ->

                        Uri.parse(annotation.item).also {
                            context.startActivity(Intent(Intent.ACTION_VIEW, it))
                        }
                    }
                }
            )


        }
    }
}