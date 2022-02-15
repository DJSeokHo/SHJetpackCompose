package com.swein.shjetpackcompose.basic.navigationexample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.shjetpackcompose.application.ui.theme.*

object NavigationExampleSubviews {

    @Composable
    fun HomeView(onButtonClick: () -> Unit) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorFFFFFF)
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Home",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Button(
                    onClick = {
                        onButtonClick()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color111111, contentColor = ColorFFFFFF),
                ) {
                    Text(text = "go to event")
                }
            }

        }
    }

    @Composable
    fun SearchView() {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorDDDDDD)
        ) {

            Text(
                text = "Search",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    @Composable
    fun ProfileView() {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorAAAAAA)
        ) {

            Text(
                text = "Profile",
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    @Composable
    fun EventView(fromWhere: String, message: String, onButtonClick: () -> Unit) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color6868AD)
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Event",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Text(
                    text = "from $fromWhere message: $message",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Button(
                    onClick = {
                        onButtonClick()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color111111, contentColor = ColorFFFFFF),
                ) {
                    Text(text = "go to event detail")
                }
            }
        }

    }

    @Composable
    fun EventDetailView(fromWhere: String, message: String) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color6868AD)
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "Event Detail",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Text(
                    text = "from $fromWhere message: $message",
                    color = Color.Black,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

            }

        }

    }

}