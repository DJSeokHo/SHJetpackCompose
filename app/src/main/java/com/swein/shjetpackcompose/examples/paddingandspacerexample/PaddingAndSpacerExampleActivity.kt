package com.swein.shjetpackcompose.examples.paddingandspacerexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class PaddingAndSpacerExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaddingAndSpacerExampleActivityContent()
        }
    }
}

@Composable
private fun PaddingAndSpacerExampleActivityContent() {
    Scaffold { // innerPadding ->


    }
}

@Preview(showBackground = true, name = "PaddingAndSpacerExampleActivity")
@Composable
fun PaddingAndSpacerExampleActivityPreview() {
    PaddingAndSpacerExampleActivityContent()
}