package com.swein.shjetpackcompose.googletutorial.ivbasic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.swein.shjetpackcompose.googletutorial.ivbasic.view.GTBasicView

class GTBasicActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GTBasicView.ContentView()
        }
    }

}
