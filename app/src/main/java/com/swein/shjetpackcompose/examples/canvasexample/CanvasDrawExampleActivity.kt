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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import com.swein.framework.utility.debug.ILog

class CanvasDrawExampleActivity : ComponentActivity() {

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
        mutableStateOf(0f)
    }

    val lastTouchY = remember {
        mutableStateOf(0f)
    }

    val path = remember {
        mutableStateOf<Path?>(Path())
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
                            MotionEvent.ACTION_DOWN -> {

                                path.value?.moveTo(motionEvent.x, motionEvent.y)

                                lastTouchX.value = motionEvent.x
                                lastTouchY.value = motionEvent.y

                                ILog.debug("???", "down ${lastTouchX.value} ${lastTouchY.value}")
                            }
                            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {

                                val historySize = motionEvent.historySize
                                for (i in 0 until historySize) {

                                    val historicalX = motionEvent.getHistoricalX(i)
                                    val historicalY = motionEvent.getHistoricalY(i)

                                    path.value?.lineTo(historicalX, historicalY)
                                }

                                path.value?.lineTo(motionEvent.x, motionEvent.y)

                                ILog.debug("???", "move up ${lastTouchX.value} ${lastTouchY.value}")

                                lastTouchX.value = motionEvent.x
                                lastTouchY.value = motionEvent.y
                            }
                        }

                        lastTouchX.value = motionEvent.x
                        lastTouchY.value = motionEvent.y

                        val tempPath = path.value
                        path.value = null
                        path.value = tempPath

                        true
                    },
                onDraw = {

                    path.value?.let {
                        drawPath(
                            path = it,
                            color = Color.Black,
                            style = Stroke(
                                width = 4.dp.toPx()
                            )
                        )
                    }

                }
            )

        }
    }
}