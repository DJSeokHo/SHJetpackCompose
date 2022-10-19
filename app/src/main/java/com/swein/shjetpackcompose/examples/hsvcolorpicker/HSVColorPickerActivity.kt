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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.framework.extension.compose.color.toHexCode
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

    val selectedColor = remember {
        mutableStateOf(Color.Red)
    }

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

            ColorMap(density, color = selectedColor.value, onColorChanged = {
                selectedColor.value = it
            })

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            Result(color = selectedColor.value)
        }
    }
}

@Composable
private fun ColorMap(
    density: Density,
    color: Color,
    onColorChanged: (color: Color) -> Unit
) {

    val paddingHorizontal = 30.dp
    val selectorRadius = 25.dp

    val colorMapWidth = with(density) {
        // because horizontal padding, the real width of color map must minus horizontal padding twice. (left and right)
        LocalConfiguration.current.screenWidthDp.dp.toPx() - paddingHorizontal.toPx() - paddingHorizontal.toPx()
    }

    val minimumDragPx = with(density) {
        // the radius of the drag able selector is 25. To drag to start position of the color map, minimum is -25dp
        -selectorRadius.toPx()
    }

    val maximumDragPx = colorMapWidth - with(density) {
        // the radius of the drag able selector is 25. To drag to end position of the color map, maximum is color map width + 25dp
        selectorRadius.toPx()
    }

    val dragOffset = remember {
        mutableStateOf(minimumDragPx)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = paddingHorizontal)
            .height(50.dp)
    ) {

        // color map
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(50))
                .background(colorMap(colorMapWidth))
                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(50))
                .pointerInput(Unit) {
                    detectTapGestures { offset ->

                        dragOffset.value = offset.x - with(density) {
                            selectorRadius.toPx()
                        }

                        val correctOffset = dragOffset.value + with(density) {
                            // the draggable position is the start part of the selector, but the drag position we want is center of the selector,
                            // so we need to plus the selector's radius as the correct offset
                            selectorRadius.toPx()
                        }

                        onColorChanged(getSelectedColor(correctOffset, colorMapWidth))
                    }
                }
        )

        // drag able item
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(dragOffset.value.roundToInt(), 0)
                }
                .size(selectorRadius * 2)
                .clip(CircleShape)
                .background(color)
                .border(width = 2.dp, color = Color.Black, shape = CircleShape)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->

                        val newValue = dragOffset.value + delta
                        dragOffset.value = newValue.coerceIn(minimumDragPx, maximumDragPx)

                        val correctOffset = dragOffset.value + with(density) {
                            // the draggable position is the start part of the selector, but the drag position we want is center of the selector,
                            // so we need to plus the selector's radius as the correct offset
                            selectorRadius.toPx()
                        }

                        onColorChanged(getSelectedColor(correctOffset, colorMapWidth))
                    }
                )
        )
    }
}

@Composable
private fun Result(color: Color) {

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
            color = color
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

@Composable
private fun Saturation(
    density: Density,
    selectedColor: Color
) {

    val paddingHorizontal = 30.dp
    val selectorRadius = 5.dp

    val colorMapWidth = with(density) {
        // because horizontal padding, the real width of color map must minus horizontal padding twice. (left and right)
        LocalConfiguration.current.screenWidthDp.dp.toPx() - paddingHorizontal.toPx() - paddingHorizontal.toPx()
    }

    val minimumDragPx = with(density) {
        // the radius of the drag able selector is 25. To drag to start position of the color map, minimum is -25dp
        -selectorRadius.toPx()
    }

    val maximumDragPx = colorMapWidth - with(density) {
        // the radius of the drag able selector is 25. To drag to end position of the color map, maximum is color map width + 25dp
        selectorRadius.toPx()
    }

    val dragOffset = remember {
        mutableStateOf(minimumDragPx)
    }

    val selectedColor = remember {
        mutableStateOf(Color.Red)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = paddingHorizontal)
            .height(10.dp)
    ) {

        // color map
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(50))
                .background(colorMap(colorMapWidth))
                .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(50))
                .pointerInput(Unit) {
                    detectTapGestures { offset ->

                        dragOffset.value = offset.x - with(density) {
                            selectorRadius.toPx()
                        }

                        val correctOffset = dragOffset.value + with(density) {
                            // the draggable position is the start part of the selector, but the drag position we want is center of the selector,
                            // so we need to plus the selector's radius as the correct offset
                            selectorRadius.toPx()
                        }

                        selectedColor.value = getSelectedColor(correctOffset, colorMapWidth)
                    }
                }
        )

        // drag able item
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(dragOffset.value.roundToInt(), 0)
                }
                .size(selectorRadius * 2)
                .clip(CircleShape)
                .background(selectedColor.value)
                .border(width = 2.dp, color = Color.Black, shape = CircleShape)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->

                        val newValue = dragOffset.value + delta
                        dragOffset.value = newValue.coerceIn(minimumDragPx, maximumDragPx)

                        val correctOffset = dragOffset.value + with(density) {
                            // the draggable position is the start part of the selector, but the drag position we want is center of the selector,
                            // so we need to plus the selector's radius as the correct offset
                            selectorRadius.toPx()
                        }

                        selectedColor.value = getSelectedColor(correctOffset, colorMapWidth)
                    }
                )
        )
    }

}



private fun getSelectedColor(dragPosition: Float, colorMapWidth: Float): Color {
    val hue = (dragPosition / colorMapWidth) * 360f
    val saturation = 1f
    val lightness = 1f
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

private fun colorMap(colorMapWidth: Float): Brush {

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
