package com.swein.shjetpackcompose.examples.skeletonexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.R
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SkeletonExampleActivity : ComponentActivity() {

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

    val profileData = remember {
        mutableStateOf(ProfileData())
    }

    val loading = remember {
        mutableStateOf(false)
    }

    val finish = remember {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()

    Surface(
        color = Color.DarkGray,
        modifier = modifier
            .fillMaxSize()
    ) {

        if (finish.value) {
            ProfileView(
                loading = loading.value,
                profileData = profileData.value,
                onSignOut = {

                    coroutineScope.launch {

                        finish.value = false

                        profileData.value = ProfileData()
                    }
                }
            )
        }
        else {
            SignView(onSignIn = {

                coroutineScope.launch {

                    loading.value = true
                    finish.value = true

                    // delay of loading data from server
                    val task = async {
                        delay(2500)
                    }

                    task.await()

                    profileData.value = ProfileData(
                        imageResource = R.drawable.coding_with_cat_icon,
                        nickname = "Coding with cat",
                        content = "Android development tutorial\nstep by step",
                    )

                    loading.value = false
                    ILog.debug("???", "loading: ${loading.value}")
                }

            })
        }
    }
}

@Composable
private fun SignView(modifier: Modifier = Modifier, onSignIn: () -> Unit) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = modifier.padding(vertical = 30.dp))

        Text(
            text = "Skeleton Example",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Yellow,
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )

        Spacer(modifier = modifier.padding(vertical = 10.dp))

        Button(
            modifier = modifier.padding(20.dp),
            onClick = {
                onSignIn()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Sign in",
                fontSize = 20.sp
            )
        }
    }
}

@Composable
private fun ProfileView(modifier: Modifier = Modifier, loading: Boolean, profileData: ProfileData, onSignOut: () -> Unit) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        SkeletonWrapperView(
            contentView = {

                if (profileData.imageResource != 0) {
                    Image(
                        painter = painterResource(id = profileData.imageResource),
                        contentScale = ContentScale.Fit,
                        contentDescription = null,
                        modifier = modifier
                            .size(200.dp)
                            .clip(CircleShape)
                    )
                }
            },
            defaultPrototypeView = {

                Box(
                    modifier =
                    modifier
                        .size(200.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )
            },
            shape = CircleShape,
            loading = loading
        )

        Spacer(modifier = modifier.padding(vertical = 30.dp))

        SkeletonWrapperView(
            contentView = {
                Text(
                    text = profileData.nickname,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )
            },
            loading = loading
        )

        Spacer(modifier = modifier.padding(vertical = 15.dp))

        SkeletonWrapperView(
            contentView = {
                Text(
                    text = profileData.content,
                    fontSize = 20.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )
            },
            defaultPrototypeView = {
                Text(
                    text = "xxxxxxxxxxxxxxxxxxxx\nxxxxxxx",
                    fontSize = 20.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                )
            },
            loading = loading
        )


        Spacer(modifier = modifier.padding(vertical = 10.dp))

        SkeletonWrapperView(
            contentView = {

                Button(
                    onClick = {
                        onSignOut()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Sign out",
                        fontSize = 20.sp
                    )
                }

            },
            loading = loading
        )

    }
}

@Composable
private fun SkeletonWrapperView(
    modifier: Modifier = Modifier,
    contentView: @Composable () -> Unit,
    defaultPrototypeView: (@Composable () -> Unit)? = null,
    shape: Shape = RectangleShape,
    loading: Boolean
) {

    val defaultSize = remember {
        mutableStateOf(IntSize.Zero)
    }

    Box(
        modifier = modifier
            .wrapContentSize()
            .clip(shape)
            .onSizeChanged { intSize ->
                defaultSize.value = intSize
            },
        contentAlignment = Alignment.Center
    ) {

        // the real view after loading data
        contentView()

        if (loading) {
            defaultPrototypeView?.let {
                // if has default prototype view, show it
                it()
            }
        }

        if (loading) {

            val animateColor = remember {
                Animatable(Color.LightGray) // default
            }

            val animationToggle = remember {
                mutableStateOf(false)
            }

            // if it is loading, show skeleton with animation

            // this is my animation, something like breathing light
            // you can DIY the animation
            if (animationToggle.value) {
                LaunchedEffect(key1 = null, block = {

                    animateColor.animateTo(
                        Color.Gray,
                        animationSpec = tween(400)
                    )

                    animationToggle.value = false
                })
            }
            else {
                LaunchedEffect(key1 = null, block = {

                    animateColor.animateTo(
                        Color.LightGray,
                        animationSpec = tween(400)
                    )

                    animationToggle.value = true
                })
            }

            Box(
                modifier = modifier
                    .background(animateColor.value)
                    .then(
                        with(LocalDensity.current) {
                            Modifier.size(
                                width = defaultSize.value.width.toDp(),
                                height = defaultSize.value.height.toDp(),
                            )
                        }
                    )
            )
        }

    }
}

data class ProfileData(
    var imageResource: Int = 0,
    var nickname: String = "",
    var content: String = ""
)