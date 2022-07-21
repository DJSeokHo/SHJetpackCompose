package com.swein.shjetpackcompose.examples.shadowbuttonexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.shjetpackcompose.R

class ShadowButtonExampleActivity : ComponentActivity() {
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        ShadowButton(
            buttonColor = Color.LightGray,
            buttonContentColor = Color.Black,
            shadowColor = Color.Gray,
            shadowBottomOffset = 12f,
            shape = RoundedCornerShape(50),
            height = 50f
        ) {
            Text(
                text = "Button",
                fontSize = 17.sp
            )
        }

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
        ) {
            ShadowButton(
                buttonColor = Color.Gray,
                buttonContentColor = Color.White,
                shadowColor = Color.DarkGray,
                shadowBottomOffset = 8f,
                height = 50f
            ) {
                Text(
                    text = "Button",
                    fontSize = 17.sp
                )
            }
        }

        Spacer(modifier = Modifier.padding(vertical = 10.dp))

        Box(
            modifier = Modifier
                .width(160.dp)
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            ShadowButton(
                buttonColor = Color.Gray,
                buttonContentColor = Color.White,
                shadowColor = Color.Black,
                shadowBottomOffset = 16f,
                shape = RoundedCornerShape(10.dp),
                height = 160f
            ) {

                Image(
                    painter = painterResource(id = R.drawable.coding_with_cat_icon),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )

            }
        }

    }

}

@Composable
fun ShadowButton(
    buttonColor: Color,
    buttonContentColor: Color,
    shadowColor: Color,
    shadowBottomOffset: Float,
    height: Float = 0f,
    shape: RoundedCornerShape = RoundedCornerShape(0),
    border: BorderStroke? = null,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {

    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isPressed = interactionSource.collectIsPressedAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp + shadowBottomOffset.dp)
    ) {

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(
                    height.dp + if (!isPressed.value) {
                        shadowBottomOffset.dp
                    } else {
                        (shadowBottomOffset * 0.5).dp
                    }
                ),
            color = shadowColor,
            shape = shape
        ) {}

        Button(
            onClick = {
                onClick?.let {
                    it()
                }
            },
            elevation = ButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor, contentColor = buttonContentColor),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(
                    top = if (!isPressed.value) {
                        0.dp
                    } else {
                        (shadowBottomOffset * 0.5).dp
                    }
                )
                .height(height.dp),
            shape = shape,
            interactionSource = interactionSource,
            border = border
        ) {
            content()
        }
    }
}
