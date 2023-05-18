package com.swein.shjetpackcompose.importantexamples.sideeffecttest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect

class LaunchedEffectActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // LaunchedEffect

            // 和 DisposableEffect 作用一样，只不过这个LaunchedEffect支持协程环境
            LaunchedEffect(key1 = Unit, block = {

            })

        }
    }
}