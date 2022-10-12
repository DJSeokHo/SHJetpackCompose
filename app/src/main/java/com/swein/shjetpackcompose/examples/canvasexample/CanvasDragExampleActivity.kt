package com.swein.shjetpackcompose.examples.canvasexample

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp

class CanvasDragExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            ContentView()

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ContentView() {

    val lastTouchX = remember {
        mutableStateOf(100f)
    }

    val lastTouchY = remember {
        mutableStateOf(100f)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .pointerInteropFilter { motionEvent ->

                        when (motionEvent.action) {

                            MotionEvent.ACTION_MOVE -> {

                                lastTouchX.value = motionEvent.x
                                lastTouchY.value = motionEvent.y

                            }
                        }

                        true
                    },
                onDraw = {

                    drawCircle(
                        color = Color.Red,
                        radius = 20.dp.toPx(),
                        center = Offset(lastTouchX.value, lastTouchY.value)
                    )

                }
            )

        }
    }
}