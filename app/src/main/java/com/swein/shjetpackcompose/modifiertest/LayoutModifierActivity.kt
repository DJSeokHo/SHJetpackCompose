package com.swein.shjetpackcompose.modifiertest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import kotlin.math.min

class LayoutModifierActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

//            Modifier.layout { measurable, constraints ->
//                // 这个非常重要，用于改变控件的尺寸和大小有关。
                // 但是这个不能用于精细的子组建摆放，应该用Layout组建才行。
//                // 比如 size, padding
//            }

            Column {

                Text(
                    "Coding with cat",
                    modifier = Modifier.layout { measurable, constraints ->
                        // measurable 是用于自我测量的目的
                        // constraints 是外部给的尺寸限制

                        // 什么都不做的话，这样写
                        // 这个和自定义view的过程比较类似，也很清晰
                        // 1 测量 2 保存 3 偏移
                        val placeable = measurable.measure(constraints) // 1
                        layout(placeable.width, placeable.height) { // 2
                            // 3

                            // 怎么摆放这个 Text 组建呢？
                            placeable.placeRelative(0, 0) // 不改变，不偏移

                        }

                        // 那么为什么要用这个呢？直接padding, size什么的不行吗？
                        // 行，但是想在一个单一的modifier里同时修改size, padding的话，那么就只能用这个layout了


//                    object : MeasureResult {
//                        override val alignmentLines: Map<AlignmentLine, Int> // 用于对齐
//                            get() = TODO("Not yet implemented")
//                        override val height: Int // 测量的高
//                            get() = placeable.height
//                        override val width: Int // 测量的宽
//                            get() = placeable.width
//
//                        override fun placeChildren() {
//                            // 用于怎么去摆放内部children组建的
//                        }
//                    }
                    }
                )

                Spacer(modifier = Modifier.padding(vertical = 20.dp))

                Box(
                    modifier = Modifier
                        .background(Color.Cyan)
                ) {

                    Text(
                        "Coding with cat",
                        modifier = Modifier
                            .layout { measurable, constraints ->

                                // 修改只能说是从外部对Text的测量，位置，尺寸进行影响
                                // 实际上是拿不到Text内部的信息的

                                val placeable = measurable.measure(constraints)

                                // 把Text修改成正方形
                                val size = min(placeable.width, placeable.height)

                                layout(size, size) {
                                    placeable.placeRelative(20, 10)
                                }
                            }
                    )
                }

                Spacer(modifier = Modifier.padding(vertical = 20.dp))

                Column(
                    modifier = Modifier
                        .background(Color.Cyan)
                        .padding(5.dp)
                ) {
                    Text(
                        "Coding with cat",
                        modifier = Modifier
                                // 调换下裂顺序，我们可以发现修改的执行顺序是从下到上(不换行的话就是右到左)
                                // 因为最终生成的是LayoutNode，修饰时用的是foldOut遍历
                            .background(Color.Red)
                            .padding(10.dp)
                    )

                    Text(
                        "Coding with cat",
                        modifier = Modifier
                            // 并且这样写的话，还是叠加的
                            /*
                            [
                                LayoutModifier - 20.dp ModifierLayoutNode
                                [
                                    LayoutModifier - 10.dp ModifierLayoutNode
                                    [
                                        Text() innerLayoutNodeWrapper - InnerPlaceable
                                    ]
                                ]
                            ]
                            先看见20dp，不知道加给谁，继续测内部又遇到10dp，继续测内部遇到实际组建，
                            于是就由内而外进行叠加，最终进行显示，得到一个30dp padding 的 Text

                            所以如果是 Modifier.size(40.dp).size(80.dp)
                            的话，那么最终的结果是40dp的尺寸

                            还有一个情况是 Modifier.requiredSize()，如果出现在非上方(非左边)的话，那么
                            他的意义就在于是否听最左边的话，如果不听，比左边大，那么最终左边会裁切他，
                            如果不听，比左边小，那么就会被空出来居中显示(比如Box)
                             */
                            .padding(20.dp)
                            .padding(10.dp)

                    )
                }
            }

        }
    }
}