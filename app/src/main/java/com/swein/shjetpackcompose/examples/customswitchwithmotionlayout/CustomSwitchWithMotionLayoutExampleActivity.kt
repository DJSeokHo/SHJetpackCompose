package com.swein.shjetpackcompose.examples.customswitchwithmotionlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import com.swein.shjetpackcompose.application.ui.theme.LocalSpacing

class CustomSwitchWithMotionLayoutExampleActivity : ComponentActivity() {
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

    val animateState = remember {
        mutableStateOf(false)
    }

    val surfaceBackgroundColor by animateColorAsState(
        targetValue = if (animateState.value) {
            Color.White
        }
        else {
            Color.Black
        },
        animationSpec = tween(600)
    )

    Surface(
        color = surfaceBackgroundColor,
        modifier = modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = modifier
                .fillMaxSize()
        ) {

            Column(
                modifier = modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = modifier.padding(vertical = LocalSpacing.current.large))

                CustomSwitch(animateState = animateState)
            }
        }
    }
}

@OptIn(ExperimentalMotionApi::class)
@Composable
private fun CustomSwitch(modifier: Modifier = Modifier, animateState: MutableState<Boolean>) {

    val progress by animateFloatAsState(
        targetValue = if (animateState.value) {
            1f
        }
        else {
            0f
        },
        animationSpec = tween(600)
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (animateState.value) {
            Color.LightGray
        }
        else {
            Color.DarkGray
        },
        animationSpec = tween(600)
    )

    val switchColor by animateColorAsState(
        targetValue = if (animateState.value) {
            Color(0xFF2196F3)
        }
        else {
            Color(0xFFF44336)
        },
        animationSpec = tween(600)
    )

    val textOffFontSize by animateIntAsState(
        targetValue = if (animateState.value) {
            16
        }
        else {
            32
        },
        animationSpec = tween(600)
    )

    val textOnFontSize by animateIntAsState(
        targetValue = if (animateState.value) {
            32
        }
        else {
            16
        },
        animationSpec = tween(600)
    )


    MotionLayout(
        start = startConstraintSet(),
        end = endConstraintSet(),
        progress = progress,
        modifier = Modifier
            .size(width = 320.dp, height = 60.dp)
    ) {

        Box(
            modifier = modifier
                .layoutId("switchBackground")
                .clip(RoundedCornerShape(50))
                .background(backgroundColor)
        )

        Box(
            modifier = modifier
                .layoutId("switch")
                .clip(RoundedCornerShape(50))
                .background(switchColor)
                .clickable {
                    animateState.value = !animateState.value
                }
        )

        Box(
            modifier = modifier
                .layoutId("textOff"),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "off",
                color = Color.White,
                fontSize = textOffFontSize.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Box(
            modifier = modifier
                .layoutId("textOn"),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "on",
                color = Color.White,
                fontSize = textOnFontSize.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun startConstraintSet(): ConstraintSet {
    return ConstraintSet {

        val switchBackground = createRefFor("switchBackground")
        val switch = createRefFor("switch")
        val textOff = createRefFor("textOff")
        val textOn = createRefFor("textOn")

        constrain(switchBackground) {
            width = Dimension.matchParent
            height = Dimension.matchParent
        }

        // switch: left side
        constrain(switch) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.value(160.dp)
            height = Dimension.matchParent
        }

        // textOff: left side
        constrain(textOff) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.value(160.dp)
            height = Dimension.matchParent
        }

        // textOn: right side
        constrain(textOn) {
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.value(160.dp)
            height = Dimension.matchParent
        }
    }
}

private fun endConstraintSet(): ConstraintSet {
    return ConstraintSet {

        val switchBackground = createRefFor("switchBackground")
        val switch = createRefFor("switch")
        val textOff = createRefFor("textOff")
        val textOn = createRefFor("textOn")

        constrain(switchBackground) {
            width = Dimension.matchParent
            height = Dimension.matchParent
        }

        // switch: right side
        constrain(switch) {
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.value(160.dp)
            height = Dimension.matchParent
        }

        // textOff: left side
        constrain(textOff) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.value(160.dp)
            height = Dimension.matchParent
        }

        // textOn: right side
        constrain(textOn) {
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            width = Dimension.value(160.dp)
            height = Dimension.matchParent
        }
    }
}

//@OptIn(ExperimentalMotionApi::class)
//@Composable
//private fun CustomSwitch(modifier: Modifier = Modifier) {
//    var animateToEnd by remember { mutableStateOf(false) }
//    val progress by animateFloatAsState(
//        targetValue = if (animateToEnd) 1f else 0f,
//        animationSpec = tween(600)
//    )
//    Column(modifier.background(Color.White)) {
//        MotionLayout(
//            ConstraintSet(
//                """ {
//
//                backgroundSwitch: {
//                    start: ['parent', 'start', 36],
//                    top: ['parent', 'top', 66],
//                    end: ['parent', 'end', 36],
//                    custom: {
//                      color: "#d2d2d2"
//                    }
//                },
//
//                buttonSwitch: {
//                    top: ['backgroundSwitch', 'top', 0],
//                    start: ['backgroundSwitch', 'start', 0]
//                },
//                light: {
//                    top: ['backgroundSwitch', 'top', 0],
//                    start: ['backgroundSwitch', 'start', 0],
//                    bottom: ['backgroundSwitch', 'bottom', 0]
//                },
//                dark: {
//                    top: ['backgroundSwitch', 'top', 0],
//                    end: ['backgroundSwitch', 'end', 0],
//                    bottom: ['backgroundSwitch', 'bottom', 0]
//                }
//             }"""
//            ),
//            ConstraintSet(
//                """ {
//                backgroundSwitch: {
//                    start: ['parent', 'start', 36],
//                    top: ['parent', 'top', 66],
//                    end: ['parent', 'end', 36],
//                    custom: {
//                      color: "#343434"
//                    }
//                },
//
//                buttonSwitch: {
//                    top: ['backgroundSwitch', 'top', 0],
//                    end: ['backgroundSwitch', 'end', 0]
//                },
//                light: {
//                    top: ['backgroundSwitch', 'top', 0],
//                    start: ['backgroundSwitch', 'start', 0],
//                    bottom: ['backgroundSwitch', 'bottom', 0]
//                },
//                dark: {
//                    top: ['backgroundSwitch', 'top', 0],
//                    end: ['backgroundSwitch', 'end', 0],
//                    bottom: ['backgroundSwitch', 'bottom', 0]
//                }
//              }"""
//            ),
//            progress = progress,
//            modifier = modifier
//                .fillMaxSize()
//                .background(Color.White)
//        ) {
//
//            Box(
//                modifier = modifier
//                    .layoutId("backgroundSwitch")
//                    .width(300.dp)
//                    .height(72.dp)
//                    .clip(RoundedCornerShape(36.dp))
//                    .clickable(onClick = { animateToEnd = !animateToEnd })
//                    .background(motionProperties("backgroundSwitch").value.color("color"))
//            )
//
//
//            Box(
//                modifier = modifier
//                    .layoutId("buttonSwitch")
//                    .width(150.dp)
//                    .height(72.dp)
//                    .clip(RoundedCornerShape(36.dp))
//                    .background(Color.Gray)
//            )
//
//            Text(
//                text = "light",
//                modifier = modifier
//                    .layoutId("light")
//                    .width(150.dp),
//                color = Color.White,
//                fontSize = 24.sp,
//                textAlign = TextAlign.Center
//            )
//            Text(
//                text = "dark",
//                modifier = modifier
//                    .layoutId("dark")
//                    .width(150.dp),
//                color = Color.Black,
//                fontSize = 24.sp,
//                textAlign = TextAlign.Center
//            )
//        }
//    }
//
//}