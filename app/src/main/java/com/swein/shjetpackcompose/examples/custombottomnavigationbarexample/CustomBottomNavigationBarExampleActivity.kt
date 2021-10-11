package com.swein.shjetpackcompose.examples.custombottomnavigationbarexample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.swein.shjetpackcompose.application.ui.theme.SHJetpackComposeTheme

class CustomBottomNavigationBarExampleActivity : ComponentActivity() {

    companion object {
        fun startFrom(context: Context) {
            Intent(context, CustomBottomNavigationBarExampleActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            SHJetpackComposeTheme() {
                Scaffold(modifier = Modifier.fillMaxSize(), backgroundColor = Color.White) {
                    CustomBottomNavigationBarView.BottomNavigationContainer {

                    }
                }
            }
        }
    }
}
