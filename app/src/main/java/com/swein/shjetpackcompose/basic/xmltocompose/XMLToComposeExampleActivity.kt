package com.swein.shjetpackcompose.basic.xmltocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.swein.shjetpackcompose.application.ui.theme.Color333333

class XMLToComposeExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            ContentView()

        }
    }

    @Composable
    private fun ContentView() {

        val isFavoriteState = remember {
            mutableStateOf(false)
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            TopPart(isFavoriteState.value) {
                isFavoriteState.value = !isFavoriteState.value
            }

            Spacer(modifier = Modifier.padding(vertical = 30.dp))

            Button(
                onClick = {
                    isFavoriteState.value = !isFavoriteState.value
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp).padding(horizontal = 10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color333333, contentColor = Color.White)
            ) {
                Text(
                    text = "Favorite",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    @Composable
    private fun TopPart(isFavorite: Boolean, onImageFavoriteClick: () -> Unit) {

        AndroidView(
            factory = { context ->
                XTCTopPartView(context, isFavorite) {
                    onImageFavoriteClick()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            update = {
                it.updateFavorite(isFavorite)
            }
        )
    }
}