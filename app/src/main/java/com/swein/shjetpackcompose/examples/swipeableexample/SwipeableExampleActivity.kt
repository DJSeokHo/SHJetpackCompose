package com.swein.shjetpackcompose.examples.swipeableexample

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

class SwipeableExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {
            ContentView()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContentView() {

    val context = LocalContext.current
    val swipeableState = rememberSwipeableState(0)

    // container view
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .swipeable(
                state = swipeableState,
                anchors = mapOf(
                    0f to 0, // original position
                    -dipToPx(context, 100f) to 1, // swipe to left, then show the right side
                    dipToPx(context, 50f) to 2 // swipe to right, then show the left side
                ),
                thresholds = { _, _ ->
                    FractionalThreshold(0.3f)
                },
                orientation = Orientation.Horizontal
            )
            .background(Color.Red)
    ) {

        // swipe able view
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(swipeableState.offset.value.roundToInt(), 0)
                }
                .fillMaxSize()
                .background(Color.DarkGray)
        )
    }
}

private fun dipToPx(context: Context, dipValue: Float): Float {
    return dipValue * context.resources.displayMetrics.density
}