package com.swein.shjetpackcompose.importantexamples.modifiertest.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.unit.dp

class DrawModifierActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // LayoutModifier 管理的是测量和布局，而DrawModifier管理的是绘制
//            DrawModifier

            // 无论是哪个 Modifier，最后都会被包进 LayoutNode 的 Modifier

            // 举个例子
            // 这个修饰得到一个40dp的有色方块。
            // background 这类属于 DrawModifier 的绘制功能，是基于右边(下边)来进行绘制的。
            Modifier
                .requiredSize(80.dp)
                .background(Color.Black)
                .requiredSize(40.dp)

            // 如何创建一个基本的DrawModifier ??
            Modifier.then(object : DrawModifier {
                override fun ContentDrawScope.draw() {

                }
            })
            // 或者
            Modifier.drawWithContent {

            }

            // 举例

            Column {

                Box(
                    modifier = Modifier
                        .background(Color.Red)
                        .size(40.dp)
                        .drawWithContent {
                            // 如果这里留空的话，会擦除下面(右边)的内容
//                        drawContent() // 除非主动调用这个，绘制原本的内容
                        }
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                // 会被擦除
                Box(
                    modifier = Modifier
                        .drawWithContent {
                            // 如果这里留空的话，会擦除下面(右边)的内容
//                            drawContent() // 除非主动调用这个，绘制原本的内容
                        }
                        .background(Color.Blue)
                        .size(40.dp) // 这个放哪里都一样，因为这个是个布局和测量的Modifier
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Box(
                    // DrawModifier本质是左边(上)包住右边(下)，
                    // 最里面的最后绘制，所以这个得到一个绿色的方块。
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Red)
                        .background(Color.Green)
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Box(
                    // DrawModifier, LayoutModifier 同时使用的情况
                    // 从右(下)到左(上)便利，所以背景色设置给了40dp的LayoutModifier
                    // 也就是说 DrawModifier 无论多少个，总是随着右边的那个LayoutModifier
                    // 比如这里的两个background就是修饰40dp的LayoutModifier
                    // 而红色背景是修饰80dp的LayoutModifier
                    modifier = Modifier
                        .background(Color.Red)
                        .requiredSize(80.dp)
                        .background(Color.Black)
                        .background(Color.Black)
                        .requiredSize(40.dp)
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                // 一个极端例子\
                Box(
                    // 其实在最终内部是一个innerPlaceable
                    // 这个innerPlaceable被修饰成距离外面8dp，而外面总40dp，
                    // 所以这个效果是一个带白色边框的24dp的蓝色方块。
                    // 记住，background这种DrawModifier总是修饰右边的LayoutModifier的。
                    modifier = Modifier
                        .size(40.dp)
                        .padding(8.dp)
                        .background(Color.Blue)
//                        .innerPlaceable
                )
            }
        }
    }
}