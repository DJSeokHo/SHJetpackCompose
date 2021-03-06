package com.swein.shjetpackcompose.basic.rowexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class RowExampleActivity : ComponentActivity() {

    /*
        @Composable inline fun Row(
            modifier: Modifier = Modifier,
            horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
            verticalAlignment: Alignment.Vertical = Alignment.Top,
            content: RowScope.() -> Unit
        ): Unit

        Row 布局能够根据使用 RowScope.weight 修改器提供的权重来分配里面子项的宽度
        如果一个子项没有提供权重的话，会使用这个子项默认的宽度，再根据其他剩余有权重的子项计算剩余的空间
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RowExamples()
        }
    }

    @Composable
    private fun RowExamples() {

        Column {
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

        Row {
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

        Row {
            Box(modifier = Modifier
                .size(30.dp)
                .background(Color.Red))
            Box(modifier = Modifier
                .height(height = 50.dp)
                .weight(1f)
                .background(Color.Green))
            Box(modifier = Modifier
                .height(height =  30.dp)
                .weight(1f)
                .background(Color.Blue))
        }
    }

    @Composable
    private fun RowExample3() {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
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
    private fun RowExample4() {

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Box(modifier = Modifier
                .size(30.dp)
                .background(Color.Red))
            Box(modifier = Modifier
                .size(50.dp)
                .background(Color.Green))
            Box(modifier = Modifier
                .width(30.dp).fillMaxHeight()
                .background(Color.Blue))
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