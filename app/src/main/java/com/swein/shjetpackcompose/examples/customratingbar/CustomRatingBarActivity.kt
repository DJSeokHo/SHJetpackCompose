package com.swein.shjetpackcompose.examples.customratingbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.swein.shjetpackcompose.R

class CustomRatingBarActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            ContentView()

        }
    }
}

@Composable
private fun ContentView(modifier: Modifier = Modifier) {

    Surface(
        color = Color.White,
        modifier = modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            RatingBar(rating = 3.7f, spaceBetween = 3.dp)

        }
    }

}

@Composable
private fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    itemCount: Int = 5,
    spaceBetween: Dp = 0.dp,
) {

    val starEmpty = ImageBitmap.imageResource(id = R.drawable.star_empty)
    val starFull = ImageBitmap.imageResource(id = R.drawable.star_full)

    val width = LocalDensity.current.run { starEmpty.width.toDp() }
    val height = LocalDensity.current.run { starEmpty.height.toDp() }

    val totalWidth = width * itemCount + spaceBetween * (itemCount - 1)


    val space = LocalDensity.current.run { spaceBetween.toPx() }

    Box(
        modifier
            .width(totalWidth)
            .height(height)
            .drawBehind {
                drawRating(rating, itemCount, starEmpty, starFull, space)
            })
}

private fun DrawScope.drawRating(
    rating: Float,
    itemCount: Int,
    imageEmpty: ImageBitmap,
    imageFull: ImageBitmap,
    space: Float
) {

    val imageWidth = imageEmpty.width.toFloat()
    val imageHeight = size.height

    val reminder = rating - rating.toInt()
    val ratingInt = (rating - reminder).toInt()

    for (i in 0 until itemCount) {

        val start = imageWidth * i + space * i

        drawImage(
            image = imageEmpty,
            topLeft = Offset(start, 0f)
        )
    }

    drawWithLayer {
        for (i in 0 until itemCount) {
            val start = imageWidth * i + space * i
            // Destination
            drawImage(
                image = imageFull,
                topLeft = Offset(start, 0f)
            )
        }

        val end = imageWidth * itemCount + space * (itemCount - 1)
        val start = rating * imageWidth + ratingInt * space
        val size = end - start

        // Source
        drawRect(
            Color.Transparent,
            topLeft = Offset(start, 0f),
            size = Size(size, height = imageHeight),
            blendMode = BlendMode.SrcIn
        )
    }
}

private fun DrawScope.drawWithLayer(block: DrawScope.() -> Unit) {
    with(drawContext.canvas.nativeCanvas) {
        val checkPoint = saveLayer(null, null)
        block()
        restoreToCount(checkPoint)
    }
}