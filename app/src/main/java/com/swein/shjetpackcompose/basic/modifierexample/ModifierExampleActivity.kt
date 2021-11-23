package com.swein.shjetpackcompose.basic.modifierexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.swein.shjetpackcompose.application.ui.theme.SHJetpackComposeTheme

/**
 * 以下为常用的修饰方法
    // 修改控件大小，第一个参数为宽度，第二个参数为高度
    size(width: Dp, height: Dp)

    // 单独修改控件宽度
    width(width: Dp)

    // 单独修改控件高度
    height(height: Dp)

    // 限制控件的宽度在一定范围内
    widthIn(min: Dp, max: Dp)

    // 限制控件高度在一定范围内
    heightIn(min: Dp,max: Dp)

    // 使得控件的宽度适应父布局大小
    fillMaxWidth()

    // 使得控件的高度适应父布局大小
    fillMaxHeight()

    // 使得控件根据内部的组件自适应内容大小
    wrapContentSize()
 */
class ModifierExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            SHJetpackComposeTheme {
                ModifierExamples()
            }

        }
    }

    @Composable
    private fun ModifierExamples() {

        Column {

            ModifierExample1()

            SplitLine()

            ModifierExample2()

            SplitLine()

            ModifierExample3()

            SplitLine()

            ModifierExample4()
        }
    }

    @Composable
    private fun ModifierExample1() {

        Card(
            elevation = 5.dp,
            /*
            add clickable, then add

            Fix the click able area, then when we add padding,
            the Card will margin 16dp inside the click able area that we had already set

            So, clickable area is big than Card area
             */
            modifier = Modifier
                .clickable { }
                .padding(16.dp)
        ) {
            Text(text = "example 1")
        }

    }


    @Composable
    private fun ModifierExample2() {

        Card(
            elevation = 5.dp,
            /*
            add clickable, then add padding

            Fix the click able area, then when we add padding,
            the Card will margin 16dp inside the click able area that we had already set

              So, clickable area is big than Card area
             */
            modifier = Modifier
                .clickable { }
                .padding(16.dp)
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Text(text = "example 2")
            }
        }

    }


    @Composable
    private fun ModifierExample3() {

        Card(
            elevation = 5.dp,
            /*
            add padding, then add clickable

            The Card will margin 16dp to its' parent, then when we add clickable,
            only the area of Card can clickable

            So, clickable area is equal to the Card area
             */
            modifier = Modifier
                .padding(16.dp)
                .clickable { }
        ) {
            Box(modifier = Modifier.padding(20.dp)) {
                Text(text = "example 3")
            }
        }

    }

    @Composable
    private fun ModifierExample4() {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp)
                .clickable { /* 不做任何事 */ },
            elevation = 8.dp
        ) {
            // 内部没有组件
        }

    }

    @Composable
    private fun SplitLine() {
        Spacer(
            Modifier
                .padding(vertical = 0.dp)
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.Black))
    }

}