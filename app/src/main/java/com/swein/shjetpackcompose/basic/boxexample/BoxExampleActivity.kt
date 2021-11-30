package com.swein.shjetpackcompose.basic.boxexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class BoxExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            BoxExamples()

        }
    }

    @Composable
    private fun BoxExamples() {

        Column {

            BoxExample1()

            SplitLine()

            BoxExample2()

            SplitLine()

            BoxExample3()

            SplitLine()

            BoxExample4()
        }
    }

    @Composable
    private fun BoxExample1() {

        Box(
            modifier = Modifier
                .background(Color.Red)
        ) {

            Text(
                text = "Example 1",
                color = Color.White,
                modifier = Modifier.padding(30.dp)
            )
        }
    }

    @Composable
    private fun BoxExample2() {

        Box(
            modifier = Modifier.size(120.dp).background(Color.Black)
        ) {

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(Color.Red))

            Text(
                text = "Example 2",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }

    }

    @Composable
    private fun BoxExample3() {

        Box(
            modifier = Modifier.size(150.dp)
        ) {

            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)) {

                Text(
                    text = "Example 3 1",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.TopCenter)
                )

                Text(
                    text = "Example 3 2",
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 10.dp, bottom = 10.dp)
                )

            }
        }

    }


    @Composable
    private fun BoxExample4() {

        val context = LocalContext.current

        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.Red),
            contentAlignment = Alignment.BottomEnd
        ) {

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(Color.Blue)) {

                Text(
                    text = "Example 4 1",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.TopCenter)
                )

                Text(
                    text = "Example 4 2",
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 10.dp, bottom = 10.dp)
                )

            }

            Box(modifier = Modifier
                .size(100.dp)
                .background(Color.Green)
                .clickable {
                    Toast
                        .makeText(context, "Example 4", Toast.LENGTH_SHORT)
                        .show()
                }
            )
        }

    }

    @Composable
    private fun SplitLine() {
        Spacer(
            Modifier
                .padding(vertical = 10.dp)
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.Black))
    }
}