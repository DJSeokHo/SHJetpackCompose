package com.swein.shjetpackcompose.basic.lazyrowexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.shjetpackcompose.R

class LazyRowExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ContentView()

        }
    }

    @Composable
    private fun ContentView() {
        LazyRowExample()
    }

    @Composable
    fun LazyRowExample() {

        val list = mutableListOf<String>()
        list.addAll((0..5).map { it.toString() })
        list.add("Coding")
        list.add("With")
        list.add("Cat")
        list.addAll((6..100).map { it.toString() })

        LazyRow(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxWidth().height(300.dp)
                .background(Color.White)
        ) {

            items(
                items = list
            ) { item ->

                when (item) {
                    "Coding", "With" -> {
                        TransparentItem(item)
                    }

                    "Cat" -> {

                        ImageAndTextItem(item)

                    }

                    else -> {

                        BackgroundItem(item)

                    }
                }

            }
        }
    }

    @Composable
    private fun BackgroundItem(item: String) {

        Text(
            text = item,
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 5.dp)
                .wrapContentWidth()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(bounded = true, color = Color.Red),
                    onClick = {
                        Toast
                            .makeText(
                                this@LazyRowExampleActivity,
                                item,
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                )
                .background(Color.LightGray)
                .padding(all = 16.dp),
            fontSize = 16.sp,
            color = Color.Black
        )
    }

    @Composable
    private fun TransparentItem(item: String) {
        Text(
            text = item,
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 5.dp)
                .wrapContentWidth()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(
                        bounded = true,
                        color = Color.Black
                    ),
                    onClick = {
                        Toast
                            .makeText(
                                this@LazyRowExampleActivity,
                                item,
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                )
                .background(Color.Transparent)
                .padding(all = 16.dp),
            fontSize = 20.sp,
            color = Color.Red
        )
    }

    @Composable
    private fun ImageAndTextItem(item: String) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 16.dp, vertical = 5.dp)
                .wrapContentWidth()
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(
                        bounded = true,
                        color = Color.White
                    ),
                    onClick = {
                        Toast
                            .makeText(
                                this@LazyRowExampleActivity,
                                item,
                                Toast.LENGTH_SHORT
                            )
                            .show()
                    }
                )
                .background(Color.DarkGray)
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = item,
                fontSize = 16.sp,
                color = Color.Yellow
            )

            Spacer(modifier = Modifier.padding(vertical = 6.dp))

            Image(
                painter = painterResource(id = R.drawable.coding_with_cat_icon),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
            )
        }

    }
}