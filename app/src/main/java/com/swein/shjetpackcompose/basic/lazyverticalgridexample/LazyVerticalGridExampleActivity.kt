package com.swein.shjetpackcompose.basic.lazyverticalgridexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LazyVerticalGridExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ContentView()

        }
    }

    @Composable
    private fun ContentView() {

        LazyVerticalGridDemo()

    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun LazyVerticalGridDemo() {
        val list = (1..20).map { it.toString() }

        LazyVerticalGrid(
//            cells = GridCells.Adaptive(128.dp),
            cells = GridCells.Fixed(3),

            // content padding
            contentPadding = PaddingValues(
                start = 12.dp,
                top = 16.dp,
                end = 12.dp,
                bottom = 16.dp
            ),
            content = {
                items(list.size) { index ->

                    Card(
                        backgroundColor = Color.LightGray,
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth().height(180.dp),
                        elevation = 8.dp,
                    ) {

                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = list[index],
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp,
                                color = Color.DarkGray,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp).align(Alignment.Center)
                            )
                        }

                    }
                }
            }
        )
    }
}