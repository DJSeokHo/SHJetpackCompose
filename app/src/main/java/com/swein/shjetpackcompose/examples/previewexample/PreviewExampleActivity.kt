package com.swein.shjetpackcompose.examples.previewexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class PreviewExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ContentView()
        }
    }
}

@DevicesPreviews
@Composable
private fun ContentView() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp)
    ) {

        CardView()
    }
}

@CustomPreviews
@Composable
private fun CardView() {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(12.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Android",
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 20.sp)
            )

            Text(
                text = "Coding with cat",
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 14.sp)
            )
        }

    }
}