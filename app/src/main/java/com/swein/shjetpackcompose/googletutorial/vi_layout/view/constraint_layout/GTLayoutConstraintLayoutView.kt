package com.swein.shjetpackcompose.googletutorial.vi_layout.view.constraint_layout

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension

object GTLayoutConstraintLayoutView {


    @Composable
    fun TwoTexts(modifier: Modifier = Modifier, text1: String, text2: String) {
        Row(modifier = modifier.height(IntrinsicSize.Min)) {
            Text(
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(start = 4.dp)
//                    .wrapContentWidth(Alignment.Start),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
                    .wrapContentWidth(Alignment.End),
                text = text1
            )

            Divider(color = Color.Black, modifier = Modifier.fillMaxHeight().width(1.dp))
            Text(
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(end = 4.dp)
//                    .wrapContentWidth(Alignment.End),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
                    .wrapContentWidth(Alignment.Start),
                text = text2
            )
        }
    }



    /**
    解耦API
    到目前为止，在示例中，约束已被内联指定，并在应用它们的可组合中使用修饰符。
    但是，在某些情况下，将约束与其应用的布局解耦是有价值的：常见示例是根据屏幕配置轻松更改约束或在 2 个约束集之间设置动画。
     */
    @Composable
    fun DecoupledConstraintLayout() {
        BoxWithConstraints {
            // maxWidth < maxHeight means Portrait
            // maxWidth > maxHeight means Landscape
            val constraints = if (maxWidth < maxHeight) {
                decoupledConstraints(margin = 16.dp) // Portrait constraints
            }
            else {
                decoupledConstraints(margin = 32.dp) // Landscape constraints
            }

            ConstraintLayout(constraints) {
                Button(
                    onClick = { /* Do something */ },
                    // set layout id
                    modifier = Modifier.layoutId("button")
                ) {
                    Text("Button")
                }

                Text(
                    text = "Text",
                    // set layout id
                    modifier = Modifier.layoutId("text")
                )
            }
        }
    }

    private fun decoupledConstraints(margin: Dp): ConstraintSet {
        return ConstraintSet {
            val button = createRefFor("button")
            val text = createRefFor("text")

            constrain(button) {
                top.linkTo(parent.top, margin = margin)
            }
            constrain(text) {
                top.linkTo(button.bottom, margin = margin)
            }
        }
    }


    @Composable
    fun LargeConstraintLayout(modifier: Modifier = Modifier) {
        ConstraintLayout {
            val text = createRef()

            val guideline = createGuidelineFromStart(fraction = 0.5f)
            Text(
                text = "This is a very very very very very very very long text",
                modifier.constrainAs(text) {
                    linkTo(start = guideline, end = parent.end)

                    /*
                    可用的Dimension行为有：

                    preferredWrapContent - 布局是包装内容，受该维度的约束。
                    wrapContent - 即使约束不允许，布局也是包装内容。
                    fillToConstraints - 布局将扩展以填充由其在该维度中的约束定义的空间。
                    preferredValue - 布局是一个固定的 dp 值，受该维度的约束。
                    value - 布局是一个固定的 dp 值，无论该维度的约束如何
                     */
                    width = Dimension.preferredWrapContent

                    // 某些Dimensions 可以被强制：
//                    width = Dimension.preferredWrapContent.atLeast(100.dp)
                }
            )
        }
    }

    @Composable
    fun ConstraintLayoutViewTwo(modifier: Modifier = Modifier) {
        ConstraintLayout(modifier = modifier.fillMaxWidth()) {
            val (button1, button2, text) = createRefs()

            Button(
                onClick = {

                },
                modifier = modifier.constrainAs(button1) {
                    top.linkTo(parent.top, margin = 16.dp)
                }
            ) {
                Text("Button 1")
            }

            Text(text = "Text", modifier = modifier.constrainAs(text) {
                top.linkTo(button1.bottom, margin = 16.dp)
                centerAround(button1.end)
            })

            // create a barrier end of button1 and text
            val barrier = createEndBarrier(button1, text)
            Button(
                onClick = { /* Do something */ },
                modifier = Modifier.constrainAs(button2) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(barrier)
                }
            ) {
                Text("Button 2")
            }
        }
    }

    @Composable
    fun ConstraintLayoutView(modifier: Modifier = Modifier) {
        ConstraintLayout(modifier = modifier.fillMaxWidth()) {

            // Create references for the composables to constrain
            val (button, text) = createRefs()

            Button(
                onClick = {

                },
                // Assign reference "button" to the Button composable
                // and constrain it to the top of the ConstraintLayout
                modifier = modifier.constrainAs(button) {
                    top.linkTo(parent.top, margin = 16.dp)

                    // Centers Text horizontally in the ConstraintLayout
                    centerHorizontallyTo(parent)
                }
            ) {
                Text("Button")
            }

            // Assign reference "text" to the Text composable
            // and constrain it to the bottom of the Button composable
            Text(text = "Text", modifier = modifier.constrainAs(text) {
                top.linkTo(button.bottom, margin = 16.dp)

                // Centers Text horizontally in the ConstraintLayout
//                centerHorizontallyTo(parent)

                // Centers Text horizontally with button
                centerHorizontallyTo(button)
            })
        }
    }



}

@Preview(showBackground = true, name = "layout constraint layout view")
@Composable
fun DefaultGTLayoutConstraintLayoutView() {
//    GTLayoutConstraintLayoutView.ConstraintLayoutView()
//    GTLayoutConstraintLayoutView.ConstraintLayoutViewTwo()
//    GTLayoutConstraintLayoutView.LargeConstraintLayout()
//    GTLayoutConstraintLayoutView.DecoupledConstraintLayout()
    GTLayoutConstraintLayoutView.TwoTexts(text1 = "Hello", text2 = "Coding with cat")
}