package com.swein.shjetpackcompose.examples.custombottomnavigationbarexample

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.shjetpackcompose.R

object CustomBottomNavigationBarView {

    @Composable
    fun BottomNavigationContainer(modifier: Modifier = Modifier, containerView: @Composable (modifier: Modifier) -> Unit) {

        Box(
            modifier = modifier.fillMaxSize()
        ) {

             Box(modifier = modifier
                 .fillMaxSize()
                 .padding(bottom = 60.dp),
            ) {
                containerView(modifier.fillMaxSize())
            }

            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .align(Alignment.BottomStart),
                color = colorResource(id = R.color.basic_color_2022)
            ) {

                Row(
                    modifier = modifier.fillMaxSize()
                ) {

                    BottomNavigationItem(
                        modifier = modifier.weight(1f),
                        imageResource = R.mipmap.ti_close,
                        text = "Menu 1"
                    )

                    BottomNavigationItem(
                        modifier = modifier.weight(1f),
                        imageResource = R.mipmap.ti_close,
                        text = "Menu 2"
                    )

                    BottomNavigationItem(
                        modifier = modifier.weight(1f),
                        imageResource = R.mipmap.ti_close,
                        text = "Menu 3"
                    )

                    BottomNavigationItem(
                        modifier = modifier.weight(1f),
                        imageResource = R.mipmap.ti_close,
                        text = "Menu 4"
                    )
                }

            }
        }
    }

    @Composable
    private fun BottomNavigationItem(modifier: Modifier, imageResource: Int, text: String) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // column 内部的 在 column 内水平居中
            verticalArrangement = Arrangement.Center, // column 内部的 view 在 column 内垂直居中
            modifier = modifier.fillMaxHeight().clickable {

            }.padding(2.dp)
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
            Text(text = text, color = Color.White, fontSize = 10.sp)
        }
        
    }
}