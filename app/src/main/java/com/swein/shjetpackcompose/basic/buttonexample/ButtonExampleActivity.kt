package com.swein.shjetpackcompose.basic.buttonexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class ButtonExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Column {

                JustButton()

                SplitLine()


            }
        }
    }

    @Composable
    private fun JustButton() {

        val context = LocalContext.current

        Button(onClick = {
            Toast.makeText(context, "click a button", Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "button")
        }
    }

    @Composable
    private fun SplitLine() {
        Spacer(
            Modifier
                .padding(vertical = 3.dp)
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.Black))
    }
}