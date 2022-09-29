package com.swein.shjetpackcompose.examples.canvasexample

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class CanvasBasicExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {
            ContentView()
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun ContentView() {

    val context = LocalContext.current
    val textMeasurer = rememberTextMeasurer()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {

        Box(modifier = Modifier
            .size(300.dp)
            .background(Color.White)
        ) {

            Canvas(modifier = Modifier
                .fillMaxSize()
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                drawRectView(this, canvasWidth, canvasHeight)
                drawCircleView(this, canvasWidth, canvasHeight)
                drawCrossLineView(this, canvasWidth, canvasHeight)

                drawBitmap(this, context, com.swein.shjetpackcompose.R.drawable.coding_with_cat_icon, canvasWidth, canvasHeight)
                drawTextView(this, canvasWidth, canvasHeight, textMeasurer)
            }

        }
    }
}

private fun drawBitmap(drawScope: DrawScope, context: Context, imageResource: Int, width: Float, height: Float) {

    val bitmapWidth = (width * 0.2f).toInt()
    val bitmapHeight = (height * 0.2f).toInt()

    val bitmap = BitmapFactory.decodeResource(context.resources, imageResource)
    val imageBitmap = Bitmap.createScaledBitmap(bitmap, bitmapWidth, bitmapHeight, true).asImageBitmap()

    val topLeftX = 100f // padding start 100 px
    val topLeftY = height - bitmapHeight - 30f // padding bottom 30 px

    drawScope.drawImage(
        image = imageBitmap,
        topLeft = Offset(topLeftX, topLeftY)
    )
}

@OptIn(ExperimentalTextApi::class)
private fun drawTextView(drawScope: DrawScope, width: Float, height: Float, textMeasurer: TextMeasurer) {

    val text = "Coding with cat"

    val textStyle = TextStyle(
        color = Color.DarkGray,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )

    val textLayoutResult: TextLayoutResult = textMeasurer.measure(
        text = AnnotatedString(text),
        style = textStyle
    )
    val textSize = textLayoutResult.size

    drawScope.drawText(
        textMeasurer = textMeasurer,
        text = text,
        topLeft = Offset(
            (width - textSize.width) * 0.5f, // in center x
            20f // padding top 20px
        ),
        style = textStyle
    )
}

private fun drawCircleView(drawScope: DrawScope, width: Float, height: Float) {

    drawScope.drawCircle(
        color = Color.Yellow,
        radius = width * 0.4f * 0.5f,
        center = Offset(width * 0.5f, height * 0.5f)
    )
}

private fun drawRectView(drawScope: DrawScope, width: Float, height: Float) {

    drawScope.drawRect(
        color = Color.Cyan,
        topLeft = Offset(width * 0.25f, height * 0.25f),
        size = Size(width * 0.5f, height * 0.5f)
    )

}

private fun drawCrossLineView(drawScope: DrawScope, width: Float, height: Float) {

    drawScope.drawLine(
        start = Offset(x = 0f, y = height * 0.5f),
        end = Offset(x = width, y = height * 0.5f),
        color = Color.Black,
        strokeWidth = 8f
    )

    drawScope.drawLine(
        start = Offset(x = width * 0.5f, y = 0f),
        end = Offset(x = width * 0.5f, y = height),
        color = Color.Black,
        strokeWidth = 8f
    )

}