package com.swein.shjetpackcompose.basic.buttonexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
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

                ColorButton()

                SplitLine()

                IconButton()

                SplitLine()

                ButtonBorder()

                SplitLine()

                ButtonShape()

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
    private fun ColorButton() {

        val context = LocalContext.current

        Row(
            modifier = Modifier.fillMaxWidth(),

            // same as linearLayout with gravity center and landscape
            horizontalArrangement = Arrangement.Center
        ) {

            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
                onClick = {
                    Toast.makeText(context, "click a button", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text(text = "button", color = Color.White)
            }

            Spacer(modifier = Modifier.padding(horizontal = 5.dp))

            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                onClick = {
                    Toast.makeText(context, "click a button", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text(text = "button")
            }

            Spacer(modifier = Modifier.padding(horizontal = 5.dp))

            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White),
                onClick = {
                    Toast.makeText(context, "click a button", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text(text = "button")
            }
        }

    }

    @Composable
    private fun IconButton() {

        val context = LocalContext.current

        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red),
            onClick = {
                Toast.makeText(context, "click a button", Toast.LENGTH_SHORT).show()
            }
        ) {
            Icon(Icons.Filled.Email, contentDescription = null, modifier = Modifier.size(ButtonDefaults.IconSize), tint = Color.White)
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = "button")
        }
    }

    @Composable
    private fun ButtonBorder() {

        val context = LocalContext.current

        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
            border = BorderStroke(2.dp, Color.Red),
            onClick = {
                Toast.makeText(context, "click a button", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(text = "button", color = Color.White)
        }

    }

    @Composable
    private fun ButtonShape() {

        val context = LocalContext.current

        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
            shape = RoundedCornerShape(100.dp),
            elevation = null, // remove the shadow around button
            onClick = {
                Toast.makeText(context, "click a button", Toast.LENGTH_SHORT).show()
            }
        ) {
            Text(text = "button", color = Color.White)
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