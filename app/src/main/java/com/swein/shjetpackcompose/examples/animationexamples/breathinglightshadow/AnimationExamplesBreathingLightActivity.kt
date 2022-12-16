package com.swein.shjetpackcompose.examples.animationexamples.breathinglightshadow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class AnimationExamplesBreathingLightActivity : ComponentActivity() {
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

    val enable = remember {
        mutableStateOf(false)
    }

    val sizeFlag = remember {
        mutableStateOf(false)
    }

    val animateSize = remember {
        Animatable(30.dp, Dp.VectorConverter)
    }

    val animateColor = remember {
        Animatable(Color.DarkGray)
    }

    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = Color.Black
    ) {

        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = modifier
                    .clip(CircleShape)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                animateColor.value,
                                Color.Transparent,
                            )
                        )
                    )
                    .padding(animateSize.value)
            ) {

                Box(
                    modifier = modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(
                            id = if (enable.value) {
                                com.swein.shjetpackcompose.R.drawable.stop
                            }
                            else {
                                com.swein.shjetpackcompose.R.drawable.play_arrow
                            }
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(100.dp)
                            .clickable {
                                enable.value = !enable.value
                            },
                        colorFilter = ColorFilter.tint(animateColor.value.copy(alpha = 0.8f))
                    )
                }
            }

        }
    }

    if (enable.value) {

        /*
        Start animation
         */

        LaunchedEffect(key1 = sizeFlag.value, block = {

            animateColor.animateTo(
                generateRandomColor(),
                animationSpec = tween(1500)
            )

            sizeFlag.value = !sizeFlag.value
        })

        LaunchedEffect(key1 = sizeFlag.value, block = {

            animateSize.animateTo(
                if (!sizeFlag.value) {
                    30.dp
                }
                else {
                    50.dp
                },
                animationSpec = tween(1500)
            )
        })
    }
    else {

        /*
        Reset
         */

        LaunchedEffect(key1 = sizeFlag.value, block = {

            animateColor.animateTo(
                Color.DarkGray,
                animationSpec = tween(5000)
            )

            sizeFlag.value = !sizeFlag.value
        })

        LaunchedEffect(key1 = sizeFlag.value, block = {

            animateSize.animateTo(
                0.dp,
                animationSpec = tween(5000)
            )
        })
    }
}

private fun generateRandomColor(): Color {
    val random = Random(System.currentTimeMillis())
    return Color(android.graphics.Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)))
}