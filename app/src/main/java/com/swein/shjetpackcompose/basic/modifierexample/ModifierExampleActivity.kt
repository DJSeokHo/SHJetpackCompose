package com.swein.shjetpackcompose.basic.modifierexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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

            ModifierExample0()

            SplitLine()

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
    private fun ModifierExample0() {

        Card(
            elevation = 5.dp,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                // add padding, then add background color
                text = "example 1",
                modifier = Modifier.padding(16.dp).background(Color.Red),
                color = Color.White
            )
        }
    }

    @Composable
    private fun ModifierExample1() {

        Card(
            elevation = 5.dp,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                // add background color, then add padding
                text = "example 1",
                modifier = Modifier.background(Color.Red).padding(16.dp),
                color = Color.White
            )
        }

    }

    @Composable
    private fun ModifierExample2() {

        val context = LocalContext.current

        Card(
            elevation = 5.dp,
            /*
            add clickable, then add padding

            Fix the click able area, then when we add padding,
            the Card will margin 16dp inside the click able area that we had already set

              So, clickable area is big than Card area
             */
            modifier = Modifier
                .clickable {
                    Toast.makeText(context, "padding after clickable", Toast.LENGTH_SHORT).show()
                }
                .padding(16.dp)
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Text(text = "example 2")
            }
        }

    }


    @Composable
    private fun ModifierExample3() {

        val context = LocalContext.current

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
                .clickable {
                    Toast.makeText(context, "clickable after padding", Toast.LENGTH_SHORT).show()
                }
        ) {
            Box(modifier = Modifier.padding(20.dp)) {
                Text(text = "example 3")
            }
        }

    }

    @Composable
    private fun ModifierExample4() {

        val context = LocalContext.current

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp)
                .clickable {
                    Toast.makeText(context, "I'm empty", Toast.LENGTH_SHORT).show()
                },
            elevation = 8.dp
        ) {

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