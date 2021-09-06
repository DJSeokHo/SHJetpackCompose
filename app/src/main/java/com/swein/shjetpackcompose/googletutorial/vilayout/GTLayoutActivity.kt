package com.swein.shjetpackcompose.googletutorial.vilayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.swein.shjetpackcompose.examples.loginscreen.view.LoginScreenOneView

class GTLayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreenOneView.ActivityContentView { _ ->

            }
        }
    }
}
