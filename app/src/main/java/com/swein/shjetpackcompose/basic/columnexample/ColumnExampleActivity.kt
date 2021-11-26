package com.swein.shjetpackcompose.basic.columnexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class ColumnExampleActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ColumnExamples()
        }
    }

    @Composable
    private fun ColumnExamples() {

        Row {

            Example1()
            SplitLine()
            Example2()
            SplitLine()
            Example3()
            SplitLine()
            Example4()
        }

    }

    @Composable
    private fun Example1() {
        Column {
            Box(modifier = Modifier
                .size(30.dp)
                .background(Color.Red))
            Box(modifier = Modifier
                .size(50.dp)
                .background(Color.Green))
            Box(modifier = Modifier
                .size(40.dp)
                .background(Color.Blue))
        }
    }

    @Composable
    private fun Example2() {
        Column {
            Box(modifier = Modifier
                .size(30.dp)
                .background(Color.Red))
            Box(modifier = Modifier
                .width(50.dp)
                .weight(1f)
                .background(Color.Green))
            Box(modifier = Modifier
                .width(40.dp)
                .weight(1f)
                .background(Color.Blue))
        }
    }

    @Composable
    private fun Example3() {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier
                .size(width = 70.dp, height = 300.dp)
                .background(Color.Red))
            Box(modifier = Modifier
                .size(width = 80.dp, height = 500.dp)
                .background(Color.Green))
            Box(modifier = Modifier
                .size(width = 60.dp, height = 600.dp)
                .background(Color.Blue))
        }
    }

    @Composable
    private fun Example4() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier
                .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {

                Box(modifier = Modifier
                    .size(30.dp)
                    .background(Color.Red))
            }

            Box(modifier = Modifier
                .size(50.dp)
                .background(Color.Blue))

            Box(modifier = Modifier
                .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Box(modifier = Modifier
                    .size(50.dp)
                    .background(Color.Green))
            }
        }
    }

    @Composable
    private fun SplitLine() {
        Spacer(
            Modifier
                .padding(horizontal = 10.dp)
                .width(1.dp)
                .fillMaxHeight()
                .background(Color.Black))
    }
}