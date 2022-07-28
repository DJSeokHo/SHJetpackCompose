package com.swein.shjetpackcompose.basic.multiplenavigationexample.profile

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
fun MNProfileControllerView() {

    MNProfileView()

}

@Composable
private fun MNProfileView() {

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(android.graphics.Color.parseColor("#6667AB"))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            TopAppBar(
                title = {
                    Text("Profile", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.DarkGray)
                },
                backgroundColor = Color.White
            )
        }
    }
}