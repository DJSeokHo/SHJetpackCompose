package com.swein.shjetpackcompose.examples.loginscreen.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

object LoginScreenOneView {

    @Composable
    fun ActivityContentView(contentView: @Composable (modifier: Modifier) -> Unit) {

        Scaffold { innerPadding ->
            contentView(Modifier.padding(innerPadding))
        }
    }



}


@Preview(showBackground = true, name = "login one view")
@Composable
fun LoginScreenOneViewPreview() {
//    SHJetpackComposeTheme {
//    }

    LoginScreenOneView.ActivityContentView { _ ->

    }
}