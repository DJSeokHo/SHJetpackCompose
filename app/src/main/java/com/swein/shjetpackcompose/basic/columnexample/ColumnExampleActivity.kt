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

            RowExample1()
            SplitLine()
            RowExample2()
            SplitLine()
            RowExample3()
            SplitLine()
            RowExample4()
        }

    }

    @Composable
    private fun RowExample1() {
        Column {
            Box(modifier = Modifier
                .size(30.dp)
                .background(Color.Red))
            Box(modifier = Modifier
                .size(50.dp)
                .background(Color.Green))
            Box(modifier = Modifier
                .size(30.dp)
                .background(Color.Blue))
        }
    }

    @Composable
    private fun RowExample2() {
        Column {
            Box(modifier = Modifier
                .size(30.dp)
                .background(Color.Red))
            Box(modifier = Modifier
                .width(50.dp)
                .weight(1f)
                .background(Color.Green))
            Box(modifier = Modifier
                .width(30.dp)
                .weight(1f)
                .background(Color.Blue))
        }
    }

    @Composable
    private fun RowExample3() {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier
                .size(width = 30.dp, height = 300.dp)
                .background(Color.Red))
            Box(modifier = Modifier
                .size(width = 50.dp, height = 500.dp)
                .background(Color.Green))
            Box(modifier = Modifier
                .size(width = 40.dp, height = 600.dp)
                .background(Color.Blue))
        }
    }

    @Composable
    private fun RowExample4() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier
                .size(30.dp)
                .background(Color.Red))
            Box(modifier = Modifier
                .size(50.dp)
                .background(Color.Green))
            Box(modifier = Modifier
                .size(30.dp)
                .background(Color.Blue))
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