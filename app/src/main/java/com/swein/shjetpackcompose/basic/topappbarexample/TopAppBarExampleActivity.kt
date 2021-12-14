package com.swein.shjetpackcompose.basic.topappbarexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.framework.compose.ripple.RippleWrapper

class TopAppBarExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TopAppBarSample(
                onBackClick = {
                    finish()
                }, onImageClick = {
                    Toast.makeText(this, "Subscribe~~", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    @Composable
    fun TopAppBarSample(onBackClick: () -> Unit, onImageClick: () -> Unit) {
        Column {
            TopAppBar(
                elevation = 4.dp,
                title = {
                    Text(text = "Coding with cat", color = Color.White, fontSize = 15.sp)
                },
                backgroundColor =  colorResource(id = com.swein.shjetpackcompose.R.color.c666666),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, null, tint = Color.White)
                    }
                }, actions = {

                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Filled.Share, null, tint = Color.White)
                    }

                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))

                    Image(
                        painter = painterResource(id = com.swein.shjetpackcompose.R.drawable.coding_with_cat_icon),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = 10.dp)
                            .clip(CircleShape)
                            .clickable(
                                interactionSource = RippleWrapper.CreateMutableInteractionSource(),
                                indication = RippleWrapper.CreateIndication(true, Color.Black),
                                onClick = onImageClick
                            )
                    )

                    Spacer(modifier = Modifier.padding(horizontal = 3.dp))

                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Filled.Settings, null)
                    }

                })

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "App Top Bar Example",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }

}