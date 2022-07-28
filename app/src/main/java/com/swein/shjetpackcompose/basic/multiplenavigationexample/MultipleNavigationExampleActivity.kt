package com.swein.shjetpackcompose.basic.multiplenavigationexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MultipleNavigationExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            MNControllerView()

        }
    }
}
