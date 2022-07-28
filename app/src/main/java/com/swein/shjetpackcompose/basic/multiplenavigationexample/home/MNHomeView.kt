package com.swein.shjetpackcompose.basic.multiplenavigationexample.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun MNHomeControllerView() {

    MNHomeView()

}

@Composable
private fun MNHomeView() {

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(android.graphics.Color.parseColor("#FF781C"))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            TopAppBar(
                title = {
                    Text("Home", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.DarkGray)
                },
                backgroundColor = Color.White
            )

        }

    }



}