package com.swein.shjetpackcompose.examples.hsvcolorpicker

import android.graphics.Color.HSVToColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.application.ui.theme.Color333333
import kotlin.math.roundToInt

class HSVColorPickerActivity : ComponentActivity() {

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

    val density = LocalDensity.current
    val paddingHorizontal = 30.dp

    // ======= color hue =======
    val colorMapWidth = LocalConfiguration.current.screenWidthDp.dp - paddingHorizontal - paddingHorizontal
    val colorMapSelectorRadius = 25.dp

    val colorMapOffsetPx = remember {
        mutableStateOf(
            with(density) {
                -colorMapSelectorRadius.toPx()
            }
        )
    }
    // ======= color hue =======

    // ======= saturation =======
    val saturationSelectorRadius = 10.dp
    val saturationOffsetPx = remember {
        mutableStateOf(
            with(density) {
                // because the saturation default value is 1, so the selector should be on right.
                (colorMapWidth - saturationSelectorRadius).toPx()
            }
        )
    }
    val saturation = remember {
        mutableStateOf(1f)
    }
    // ======= saturation =======

    // ======= lightness =======
    val lightnessSelectorRadius = 10.dp
    val lightnessOffsetPx = remember {
        mutableStateOf(
            with(density) {
                // because the lightness default value is 1, so the selector should be on right.
                (colorMapWidth - lightnessSelectorRadius).toPx()
            }
        )
    }
    val lightness = remember {
        mutableStateOf(1f)
    }
    // ======= lightness =======

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color333333
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            ColorMapSelector(
                density = density,
                paddingHorizontal = paddingHorizontal,
                colorMapWidth = colorMapWidth,
                colorMapSelectorRadius = colorMapSelectorRadius,
                colorMapOffsetPx = colorMapOffsetPx.value,
                onColorMapOffsetPx = {
                    colorMapOffsetPx.value = it
                },
                saturation = saturation.value,
                lightness = lightness.value,
//                saturation = 1f,
//                lightness = 1f
            )

            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            Text(
                text = "saturation",
                fontSize = 14.sp,
                color = Color.White
            )
            // saturation
            SLSelector(
                density = density,
                paddingHorizontal = paddingHorizontal,
                slWidth = colorMapWidth, // width is as same as color map
                slSelectorRadius = saturationSelectorRadius,
                slOffsetPx = saturationOffsetPx.value
            ) {
                saturationOffsetPx.value = it

                val correctOffset = calculateCorrectOffset(
                    selectorOffset = saturationOffsetPx.value,
                    selectorRadius = with(density) {
                        saturationSelectorRadius.toPx()
                    }
                )

                saturation.value = correctOffset / (with(density) { colorMapWidth.toPx() })
            }

            Spacer(modifier = Modifier.padding(vertical = 4.dp))

            Text(
                text = "lightness",
                fontSize = 14.sp,
                color = Color.White
            )
            // lightness
            SLSelector(
                density = density,
                paddingHorizontal = paddingHorizontal,
                slWidth = colorMapWidth, // width is as same as color map
                slSelectorRadius = lightnessSelectorRadius,
                slOffsetPx = lightnessOffsetPx.value
            ) {
                lightnessOffsetPx.value = it

                val correctOffset = calculateCorrectOffset(
                    selectorOffset = lightnessOffsetPx.value,
                    selectorRadius = with(density) {
                        lightnessSelectorRadius.toPx()
                    }
                )

                lightness.value = correctOffset / (with(density) { colorMapWidth.toPx() })
            }

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            Result(
                color = getSelectedColor(
                    colorMapOffset = calculateCorrectOffset(
                        selectorOffset = colorMapOffsetPx.value,
                        selectorRadius = with(density) {
                            colorMapSelectorRadius.toPx()
                        }
                    ),
                    colorMapWidth = with(density) {
                        colorMapWidth.toPx()
                    },
                saturation = saturation.value,
                lightness = lightness.value,
//                    saturation = 1f,
//                    lightness = 1f
                )
            )
        }
    }
}

@Composable
private fun ColorMapSelector(
    density: Density,
    paddingHorizontal: Dp,
    colorMapWidth: Dp,
    colorMapSelectorRadius: Dp,
    colorMapOffsetPx: Float,
    onColorMapOffsetPx: (colorMapOffsetPx: Float) -> Unit,
    saturation: Float,
    lightness: Float
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = paddingHorizontal)
            .height(colorMapSelectorRadius * 2)
    ) {

        // color map
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(50))
                .background(
                    createColorMap(
                        with(density) {
                            colorMapWidth.toPx()
                        }
                    )
                )
                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(50))
                .pointerInput(Unit) {
                    detectTapGestures { offset ->

                        ILog.debug("???", "tap offset $offset")

                        val offsetPx = offset.x - with(density) {
                            // the tapped position need to minus the selector's radius as the correct position for the selector
                            colorMapSelectorRadius.toPx()
                        }

                        ILog.debug("???", "offset $offsetPx")
                        onColorMapOffsetPx(offsetPx)
                    }
                }
        )

        // color selector
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(colorMapOffsetPx.roundToInt(), 0)
                }
                .size(colorMapSelectorRadius * 2)
                .clip(CircleShape)
                .background(
                    getSelectedColor(
                        colorMapOffset = calculateCorrectOffset(
                            selectorOffset = colorMapOffsetPx,
                            selectorRadius = with(density) {
                                colorMapSelectorRadius.toPx()
                            }
                        ),
                        colorMapWidth = with(density) {
                            colorMapWidth.toPx()
                        },
                        saturation = saturation,
                        lightness = lightness
                    )
                )
                .border(width = 2.dp, color = Color.Black, shape = CircleShape)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->

                        val offsetPx = (colorMapOffsetPx + delta).coerceIn(
                            /*
                            keep the drag in the range that the minimum is zero minus selector's radius
                            and the maximum is the color map's width minus selector's radius
                             */
                            with(density) {
                                -colorMapSelectorRadius.toPx()
                            },
                            with(density) {
                                (colorMapWidth - colorMapSelectorRadius).toPx()
                            }
                        )

                        ILog.debug("???", "drag $offsetPx")
                        onColorMapOffsetPx(offsetPx)
                    }
                )
        )

    }
}

/**
 * Saturation or Lightness
 */
@Composable
private fun SLSelector(
    density: Density,
    paddingHorizontal: Dp,
    slWidth: Dp,
    slSelectorRadius: Dp,
    slOffsetPx: Float,
    onSLOffsetPx: (slOffsetPx: Float) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = paddingHorizontal)
            .height(slSelectorRadius * 2)
    ) {

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.White)
                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(50))
                .pointerInput(Unit) {
                    detectTapGestures { offset ->

                        ILog.debug("???", "tap offset $offset")

                        val offsetPx = offset.x - with(density) {
                            // the tapped position need to minus the selector's radius as the correct position for the selector
                            slSelectorRadius.toPx()
                        }

                        ILog.debug("???", "offset $offsetPx")
                        onSLOffsetPx(offsetPx)
                    }
                }
        )

        Box(
            modifier = Modifier
                .offset {
                    IntOffset(slOffsetPx.roundToInt(), 0)
                }
                .size(slSelectorRadius * 2)
                .clip(CircleShape)
                .background(Color.White)
                .border(width = 2.dp, color = Color.Black, shape = CircleShape)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->

                        val offsetPx = (slOffsetPx + delta).coerceIn(
                            /*
                            keep the drag in the range that the minimum is zero minus selector's radius
                            and the maximum is the color map's width minus selector's radius
                             */
                            with(density) {
                                -slSelectorRadius.toPx()
                            },
                            with(density) {
                                (slWidth - slSelectorRadius).toPx()
                            }
                        )

                        ILog.debug("???", "drag $offsetPx")
                        onSLOffsetPx(offsetPx)
                    }
                )
        )

    }
}

private fun calculateCorrectOffset(selectorOffset: Float, selectorRadius: Float): Float {
    return selectorOffset + selectorRadius
}

@Composable
private fun Result(
    color: Color
) {

    Column(
        modifier = Modifier
            .wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 60.dp)
                .aspectRatio(1f),
            elevation = 5.dp,
            color = color,
            shape = RoundedCornerShape(8.dp)
        ) {}

        Spacer(modifier = Modifier.padding(vertical = 6.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "R: ${color.red * 255}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.padding(horizontal = 6.dp))

            Text(
                text = "G: ${color.green * 255}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.padding(horizontal = 6.dp))

            Text(
                text = "B: ${color.blue * 255}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 2.dp))

        Text(
            text = color.toHexCode(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

// extension
fun Color.toHexCode(): String {
    val red = this.red * 255
    val green = this.green * 255
    val blue = this.blue * 255
    return String.format("#%02x%02x%02x", red.toInt(), green.toInt(), blue.toInt())
}

private fun getSelectedColor(colorMapOffset: Float, colorMapWidth: Float, saturation: Float, lightness: Float): Color {
    val hue = (colorMapOffset / colorMapWidth) * 360f
    return Color(
        HSVToColor(
            floatArrayOf(
                hue,
                saturation,
                lightness
            )
        )
    )
}

private fun createColorMap(colorMapWidth: Float): Brush {

    val colors = mutableListOf<Color>()

    for (i in 0..360) {
        val saturation = 1f
        val lightness = 1f
        val hsv = HSVToColor(
            floatArrayOf(
                i.toFloat(),
                saturation,
                lightness
            )
        )

        colors.add(Color(hsv))
    }

    return Brush.horizontalGradient(
        colors = colors,
        startX = 0f,
        endX = colorMapWidth
    )
}
