package com.swein.shjetpackcompose.importantexamples.modifiertest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.swein.framework.utility.debug.ILog

class OnGloballyPositionedModifierActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // OnGloballyPositionedModifier 全局定位
            // 当我们想要获取一个区域的尺寸和位置回调的时候，可以用这个
            // 但是 OnGloballyPositionedModifier 和 OnPlacedModifier 有什么区别呢？
            // 在于回掉的时机不同。OnPlacedModifier是测量完成后，摆放的时候回掉
            // OnGloballyPositionedModifier 是对于在整个窗口中组件位置改变了的时候，就会回掉
            // 先考虑用 OnPlacedModifier， 因为轻, 消耗就小。

            val offsetX = remember {
                mutableStateOf(0)
            }

            val offsetY = remember {
                mutableStateOf(0)
            }

            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .size(200.dp)
            ) {

                Box(
                    modifier = Modifier
                        .onPlaced {
                            ILog.debug("onPlaced", "${it.positionInParent().x} ${it.positionInParent().y}")

                            offsetX.value = 100
                            offsetY.value = 50
                        }
                        .onGloballyPositioned {
                            ILog.debug("onGloballyPositioned", "${it.positionInParent().x} ${it.positionInParent().y}")
                        }
                        .offset {
                            IntOffset(offsetX.value, offsetY.value)
                        }
                        .background(Color.Red)
                        .size(100.dp)

                )

            }

//            Text(
//                modifier = Modifier
//                    .background(Color.Cyan)
//                    .onGloballyPositioned {
//
//                        ILog.debug("padding", "${it.size.width} ${it.size.height}")
//                        ILog.debug("padding", "${it.positionInParent().x} ${it.positionInParent().y}")
//                    }
//                    .padding(30.dp)
//                    .onGloballyPositioned {
//                        ILog.debug("size", "${it.size.width} ${it.size.height}")
//                    }
//                    .size(30.dp),
//                text = "Coding with cat"
//            )

        }
    }
}