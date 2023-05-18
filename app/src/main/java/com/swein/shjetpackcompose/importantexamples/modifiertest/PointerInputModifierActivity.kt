package com.swein.shjetpackcompose.importantexamples.modifiertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.R

class PointerInputModifierActivity : ComponentActivity() {

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            /**
             * 点击的Modifier同理，也是针对他右边(下方)的那个LayoutModifier作用的。如果右边(下方)没有了，那就是针对那个原始控件
             */

//            Modifier.clickable {  }
//            Modifier.combinedClickable(
//                onLongClick = {
//                    ILog.debug("???", "long click")
//                },
//                onClick = {
//                    ILog.debug("???", "click")
//                }
//            )
            Box(
               Modifier
                   .size(100.dp)
                   .background(Color.Black)
//                   .combinedClickable(
//                       onLongClick = {
//                           ILog.debug("???", "long click")
//                       },
//                       onClick = {
//                           ILog.debug("???", "click")
//                       }
//                   )
                   .pointerInput(Unit) {
//                        // 用于自定义，这是比 combinedClickable 更底层的写法
//                        detectTapGestures(
//                            onTap = {
//                                // tap 和 click ? tap强调触摸了屏幕，所以鼠标点击的话只会触发click，不会触发tap
//                            },
//                            onDoubleTap = {
//
//                            },
//                            onPress = {
//
//                            },
//                            onLongPress = {
//
//                            }
//                        )

//                       // 更底层的， 用于自定义处理各种触摸事件(不是手势)
//                       awaitPointerEventScope {
//                           // 这样写的话只能监听一次事件，出了括号就没了，再也无法继续监听
//                           val down = awaitFirstDown() // 获取一个按下事件
//                       }
//
//                       // 更底层的， 用于自定义处理各种触摸事件(不是手势)
//                       awaitEachGesture {
//                           // 这样写的话可以一直循环监听所有事件
//                           val down = awaitFirstDown() // 获取一个按下事件
//                       }
                    }
            )

        }
    }
}