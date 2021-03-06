package com.swein.shjetpackcompose.examples.loginscreen.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

object LoginScreenOneView {

    @Composable
    fun ActivityContentView() {

        Scaffold { innerPadding ->
            ContentView(Modifier.padding(innerPadding))
        }
    }

    @Composable
    private fun ContentView(modifier: Modifier) {

    }

}


@Preview(showBackground = true, name = "login one view")
@Composable
fun LoginScreenOneViewPreview() {
//    SHJetpackComposeTheme {
//    }

    LoginScreenOneView.ActivityContentView()
}