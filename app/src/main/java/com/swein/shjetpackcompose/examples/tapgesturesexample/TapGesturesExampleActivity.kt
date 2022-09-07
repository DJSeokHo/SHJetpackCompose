package com.swein.shjetpackcompose.examples.tapgesturesexample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.pow
import kotlin.math.sqrt

class TapGesturesExampleActivity : ComponentActivity() {

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

    val size = remember {
        mutableStateOf(200)
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier
                    .size(size.value.dp)
                    .background(Color.Gray)
                    .pointerInput(Unit) {

                        val fingerCount = 2

                        var previousDistance = 0f

                        forEachGesture {

                            awaitPointerEventScope {

                                do {
                                    val event = awaitPointerEvent()
                                    if (event.changes.size == fingerCount) {

                                        val firstX = event.changes[0].position.x
                                        val firstY = event.changes[0].position.y

                                        val secondX = event.changes[1].position.x
                                        val secondY = event.changes[1].position.y

                                        val currentDistance = sqrt((secondX - firstX).pow(2) + (secondY - firstY).pow(2))

                                        if (currentDistance - previousDistance > 10) {

                                            Log.d("???", "bigger")
                                            // bigger, maximum is 300
                                            if (size.value < 300) {
                                                size.value += 3
                                            }

                                            previousDistance = currentDistance
                                        }
                                        else if (previousDistance - currentDistance > 10) {

                                            Log.d("???", "smaller")

                                            // smaller, minimum is 150
                                            if (size.value > 150) {
                                                size.value -= 3
                                            }

                                            previousDistance = currentDistance
                                        }
                                    }
                                }
                                while (event.changes.any {
                                        it.pressed
                                })
                            }
                        }
                    }
            )
        }
    }
}