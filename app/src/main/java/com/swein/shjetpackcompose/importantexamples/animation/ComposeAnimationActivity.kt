package com.swein.shjetpackcompose.importantexamples.animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.R
import kotlinx.coroutines.launch

class ComposeAnimationActivity : AppCompatActivity() {

    // 背景：现在安卓基本只用属性动画，是用于旧的view系统
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // 状态转移型动画
//            AnimateXxxAsStateView()

            // 复杂动画
            AnimatableView()
        }
    }
}

/**
 * animateXxxAsState 的内部其实就是 Animatable，animateXxxAsState是扩展，更加便捷，而不是扩展了功能
 * 定位：有初始值的动画，复杂的动画，流程定制型动画
 */
@Composable
private fun AnimatableView() {

    val big = remember {
        mutableStateOf(false)
    }

    val animatable = remember {
        Animatable(
            initialValue = 48.dp, // 初始值
            typeConverter = Dp.VectorConverter
        )
    }

    Box(
        modifier = Modifier
            .size(animatable.value)
            .background(Color.Green)
            .clickable {
                big.value = !big.value
            }
    )

    LaunchedEffect(key1 = big.value, block = {
        animatable.snapTo(if (big.value) { 192.dp } else { 0.dp }) // snapTo可以瞬间完成动画，设置了动画的初始值
        animatable.animateTo(
            targetValue = if (big.value) { 96.dp } else { 48.dp }
        )
    })
}

/**
 * 而在compose里做动画是新的动画API，叫做，状态转移型动画，animateXxxAsState()，不需要初始值
 * 定位：简单的动画，这是Animatable的扩展，专门用于状态转移型动画
 */
@Composable
private fun AnimateXxxAsStateView() {

    // 1. 直接改变无动画
//    val size = remember {
//        mutableStateOf(48.dp)
//    }

    val big = remember {
        mutableStateOf(false)
    }

    // 2 animateDpAsState 会去渐变目标值
    val size = animateDpAsState(
        targetValue = if (big.value) { 96.dp } else { 48.dp }, label = ""
    )

    Box(
        modifier = Modifier
            .size(size.value)
            .background(Color.Green)
            .clickable {

                // 1. 直接改变无动画
//                size.value = 96.dp

                // 2 animateDpAsState 会去渐变目标值
                big.value = !big.value

            }
    )
}