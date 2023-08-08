package com.swein.shjetpackcompose.importantexamples.modifiertest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.layout.positionInWindow
import com.swein.framework.utility.debug.ILog

class OnPlacedModifierActivity : ComponentActivity() {

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // OnPlacedModifier 这个也可以作为一个回掉，但是不是在被测量后进行回掉，而是在被摆放时进行回掉
            // OnPlacedModifier 包含更多的信息，比如组件的位置

            Column {

                Text(
                    modifier = Modifier
                        .onSizeChanged {
                            ILog.debug("onSizeChanged", "${it.width} ${it.height}")
                        }
                        .onPlaced {
                            ILog.debug("onPlaced", "${it.size.width} ${it.size.height}")
                            ILog.debug(
                                "onPlaced",
                                "${it.positionInParent().x} ${it.positionInParent().y}"
                            )
                            ILog.debug(
                                "onPlaced",
                                "${it.positionInRoot().x} ${it.positionInRoot().y}"
                            )
                            ILog.debug(
                                "onPlaced",
                                "${it.positionInWindow().x} ${it.positionInWindow().y}"
                            )
                        },
                    text = "Coding with cat"
                )

                // 还有一个 LookaheadOnPlacedModifier 和 OnPlacedModifier 几乎一样
                // 那么区别是什么？ 用途是什么？
                // 区别就是这个叫做前瞻性测量
                // 用途呢？用于界面改变时方便添加一种平滑的过度动画。比如两个方块一大一小，想要交换大小时，加上平滑动画过渡

                LookaheadScope(content = {
                    // 前瞻性测量

                    Row(
                        modifier = Modifier
//                            .intermediateLayout { measurable, constraints ->
//                                measurable.measure(constraints)
//                            }
                            .onPlaced {

                            }
                    ) {

                    }
                })

            }
        }
    }
}