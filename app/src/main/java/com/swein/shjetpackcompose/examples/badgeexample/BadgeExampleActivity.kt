package com.swein.shjetpackcompose.examples.badgeexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.shjetpackcompose.R

class BadgeExampleActivity : ComponentActivity() {
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

    val badge = remember {
        mutableStateOf(false)
    }

    val badgeNumber = remember {
        mutableIntStateOf(0)
    }

    Surface(
        color = Color.DarkGray
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

//            Image(
//                painter = painterResource(id = R.drawable.coding_with_cat_icon),
//                contentDescription = null,
//                contentScale = ContentScale.Fit,
//                modifier = Modifier
//                    .size(100.dp)
//                    .padding(4.dp)
////                    .drawWithContent {
////
////                        this.drawContent()
////
////                        if (badge.value) {
////                            this.drawCircle(
////                                Color.Red,
////                                8.dp.toPx(),
////                                Offset(size.width - 6.dp.toPx(), 6.dp.toPx())
////                            )
////                        }
////
////                    }
//                    .badge(badge.value, 8.dp, 6.dp, 6.dp)
//                    .clip(RoundedCornerShape(6.dp))
//            )


//            Spacer(modifier = Modifier.padding(vertical = 20.dp))

//            IconButton(
//                onClick = { },
//            ) {
//                Icon(
//                    Icons.Filled.Notifications,
//                    null,
//                    tint = Color.White,
//                    modifier = Modifier
//                        .size(60.dp)
//                        .badge(badge.value, 6.dp, 15.dp, 15.dp)
//
//                )
//            }

            BadgeNumberWrapperView(
                badgeNumber = badgeNumber.intValue,
                contentView = {

                    Image(
                        painter = painterResource(id = R.drawable.coding_with_cat_icon),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp)
                            .clip(RoundedCornerShape(6.dp))
                    )
                },
                size = 30.dp,
                fontSize = 13.sp,
                padding = 0.dp
            )

            Spacer(modifier = Modifier.padding(vertical = 20.dp))

            BadgeNumberWrapperView(
                badgeNumber = badgeNumber.intValue,
                contentView = {

                    IconButton(
                        onClick = { },
                    ) {
                        Icon(
                            Icons.Filled.Notifications,
                            null,
                            tint = Color.White,
                            modifier = Modifier
                                .size(60.dp)

                        )
                    }
                }
            )

            Spacer(modifier = Modifier.padding(vertical = 40.dp))

//            Button(
//                onClick = {
//                    badge.value = !badge.value
//                },
//                colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.Black)
//            ) {
//                Text(
//                    text = "toggle badge"
//                )
//            }

            Button(
                onClick = {
                    badgeNumber.intValue++
                },
                colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.Black)
            ) {
                Text(
                    text = "add badge number"
                )
            }
        }
    }
}

@Composable
private fun BadgeNumberWrapperView(badgeNumber: Int, contentView: @Composable () -> Unit, size: Dp = 20.dp, fontSize: TextUnit = 11.sp, padding: Dp = 4.dp) {

    Box {

        contentView()

        if (badgeNumber != 0) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .size(size)
                    .clip(CircleShape)
                    .background(Color.Red)
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (badgeNumber > 99) {
                        ".."
                    }
                    else {
                        badgeNumber.toString()
                     },
                    color = Color.White,
                    fontSize = fontSize,
                    maxLines = 1
                )
            }
        }
    }
}

fun Modifier.badge(toggle: Boolean, radius: Dp, topPadding: Dp, rightPadding: Dp): Modifier {
    return this.drawWithContent {
        this.drawContent()

        if (toggle) {
            this.drawCircle(
                Color.Red,
                radius.toPx(),
                Offset(size.width - rightPadding.toPx(), topPadding.toPx())
            )
        }
    }
}