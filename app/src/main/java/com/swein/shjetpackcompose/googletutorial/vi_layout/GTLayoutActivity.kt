package com.swein.shjetpackcompose.googletutorial.vi_layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.swein.shjetpackcompose.application.ui.theme.SHJetpackComposeTheme
import com.swein.shjetpackcompose.googletutorial.vi_layout.view.GTLayoutView

class GTLayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SHJetpackComposeTheme {
                GTLayoutView.ContentView()
            }
        }
    }
}
