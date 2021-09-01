package com.swein.shjetpackcompose.googletutorial.iv_basic.view

import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.swein.shjetpackcompose.application.ui.theme.SHJetpackComposeTheme

object GTBasicView {



    @Composable
    fun Greeting(name: String) {
        Surface(color = Color.Yellow) {
            Text (text = "Hello $name!")
        }
    }

}

@Preview(showBackground = true, name = "Text preview")
@Composable
fun DefaultPreview() {
    SHJetpackComposeTheme {
        GTBasicView.Greeting(name = "Android")
    }
}