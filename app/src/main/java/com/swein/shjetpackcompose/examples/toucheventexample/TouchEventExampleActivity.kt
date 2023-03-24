package com.swein.shjetpackcompose.examples.toucheventexample

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.R


class TouchEventExampleActivity : ComponentActivity() {

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

    val context = LocalContext.current
//    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.test_image_1)
    val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.coding_with_cat_icon)

    val red = remember {
        mutableStateOf(0)
    }

    val green = remember {
        mutableStateOf(0)
    }

    val blue = remember {
        mutableStateOf(0)
    }

    val alpha = remember {
        mutableStateOf(0)
    }

    val xRatioForBitmap = remember {
        mutableStateOf(1f)
    }

    val yRatioForBitmap = remember {
        mutableStateOf(1f)
    }

    Surface(
        color = Color.Gray,
        modifier = Modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Test Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 10.dp)
                        .onSizeChanged {
                            ILog.debug("???", "Image size is ${it.width}, ${it.height}")
                            ILog.debug("???", "Bitmap size is ${bitmap.width}, ${bitmap.height}")

                            xRatioForBitmap.value = bitmap.width.toFloat() / it.width.toFloat()
                            yRatioForBitmap.value = bitmap.height.toFloat() / it.height.toFloat()
                        }
                        .pointerInteropFilter { motionEvent: MotionEvent ->

                            when (motionEvent.action) {

                                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE, MotionEvent.ACTION_UP -> {

                                    val touchXToBitmap: Float = motionEvent.x * xRatioForBitmap.value
                                    val touchYToBitmap: Float = motionEvent.y * yRatioForBitmap.value

                                    // make sure the touch event is inside bitmap
                                    if (touchXToBitmap < 0 || touchYToBitmap < 0) {
                                        return@pointerInteropFilter true
                                    }
                                    if (touchXToBitmap > bitmap.width || touchYToBitmap > bitmap.height) {
                                        return@pointerInteropFilter true
                                    }

                                    // get bitmap
                                    val pixel: Int = bitmap.getPixel(
                                        touchXToBitmap.toInt(),
                                        touchYToBitmap.toInt()
                                    )

                                    red.value = android.graphics.Color.red(pixel)
                                    green.value = android.graphics.Color.green(pixel)
                                    blue.value = android.graphics.Color.blue(pixel)
                                    alpha.value = android.graphics.Color.alpha(pixel)
                                }
                            }

                            true
                        },
                    contentScale = ContentScale.FillWidth
                )

                Spacer(modifier = Modifier.padding(vertical = 20.dp))

                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(
                            Color(
                                android.graphics.Color.argb(
                                    alpha.value,
                                    red.value,
                                    green.value,
                                    blue.value
                                )
                            )
                        )
                )
            }

        }

    }
}
