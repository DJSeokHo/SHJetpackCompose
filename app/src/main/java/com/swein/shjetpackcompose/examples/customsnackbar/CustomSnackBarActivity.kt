package com.swein.shjetpackcompose.examples.customsnackbar

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.shjetpackcompose.R
import kotlinx.coroutines.launch


class CustomSnackBarActivity : ComponentActivity() {
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

    val context = LocalContext.current

    val snackState = remember {
        SnackbarHostState()
    }

    val coroutineScope = rememberCoroutineScope()

    Surface(
        color = Color.DarkGray,
        modifier = modifier
            .fillMaxSize()
    ) {

        Box(
            modifier = modifier
                .fillMaxSize()
        ) {

            Button(
                modifier = modifier
                    .align(Alignment.Center),
                onClick = {

                    coroutineScope.launch {
                        snackState.showSnackbar("Custom Snackbar")
                    }

                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.DarkGray)
            ) {
                Text(
                    text = "Show SnackBar",
                    fontSize = 16.sp
                )
            }

            SnackbarHost(
                modifier = modifier.align(Alignment.BottomCenter),
                hostState = snackState
            ) {

                CustomSnackBar(
                    title = "Coding with cat",
                    content = "Android development tutorial step by step",
                    profileImageResource = R.drawable.coding_with_cat_icon,
                    actionImageResource = R.drawable.youtube_icon,
                    onAction = {

                        Uri.parse("https://www.youtube.com/@codingwithcat/videos").also {
                            context.startActivity(Intent(Intent.ACTION_VIEW, it))
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun CustomSnackBar(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    profileImageResource: Int,
    actionImageResource: Int,
    onAction: () -> Unit
) {

    Snackbar(
        elevation = 0.dp, // remove the shadow
        backgroundColor = Color.Transparent
    ) {

        Box(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            Column(
                modifier = modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(color = android.graphics.Color.parseColor("#00CEFF")),
                                Color(color = android.graphics.Color.parseColor("#FB9C88"))
                            )
                        )
                    )
                    .padding(start = 78.dp, top = 8.dp, bottom = 12.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {

                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Spacer(modifier = modifier.padding(vertical = 4.dp))

                Text(
                    text = content,
                    color = Color.White,
                    fontStyle = FontStyle.Italic,
                    fontSize = 15.sp
                )
            }

            Column(
                modifier = modifier
                    .padding(start = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Card(
                    elevation = 6.dp,
                    shape = RoundedCornerShape(8.dp),
                ) {

                    Image(
                        painter = painterResource(id = profileImageResource),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = modifier
                            .size(50.dp)
                    )
                }

                Spacer(modifier = modifier.padding(vertical = 6.dp))

                Image(
                    painter = painterResource(id = actionImageResource),
                    contentDescription = null,
                    modifier = modifier
                        .size(30.dp)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            onClick = onAction
                        )
                )
            }
        }
    }
}
