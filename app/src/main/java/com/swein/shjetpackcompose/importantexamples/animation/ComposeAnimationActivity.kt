package com.swein.shjetpackcompose.importantexamples.animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.KeyframesSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
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
//            AnimatableView()

            // 配置复杂动画
//            AnimationSpecView()

            // 渐变动画
//            AnimationTweenSpecView()

            // 无动画过程，立刻改变
//            AnimationSnapSpecView()

            // 可以定制关键帧动画
            AnimationKeyFrameSpecView()

        }
    }
}

/**
 * KeyFrameSpec是一个分段式的TweenSpec
 */
@Composable
private fun AnimationKeyFrameSpecView() {

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
        animatable.animateTo(
            targetValue = if (big.value) { 96.dp } else { 48.dp },
            animationSpec = keyframes {

                48.dp at 0 using FastOutLinearInEasing // 默认是 LinearEasing, 匀速

                // 插入关键帧, 动画时长默认是300毫秒
//                144.dp.at(150) // 和下面是一样的，下面是中缀写法，用空格来调用这个at函数，中缀写法看起来更加直观
//                144.dp at 150 // 在第150毫秒的时候，值是144dp,
//                144.dp at 150 with FastOutSlowInEasing // 在第150毫秒的时候，值是144dp, 并且在这一段动画设置先加速再减速的曲线(with 被弃用了)
                144.dp at 150 using FastOutSlowInEasing // 在第150毫秒的时候，值是144dp, 并且在这一段动画设置先加速再减速的曲线
                20.dp at 300 // 在第300毫秒的时候，值是20dp,

                durationMillis = 450 // 动画时长 450毫秒
                delayMillis = 500 // 延时 500毫秒再执行

            }
//            animationSpec = KeyframesSpec(KeyframesSpec.KeyframesSpecConfig<Dp>().apply {
//                // 插入关键帧, 动画时长默认是300毫秒
////                144.dp.at(150) // 和下面是一样的，下面是中缀写法，用空格来调用这个at函数，中缀写法看起来更加直观
////                144.dp at 150 // 在第150毫秒的时候，值是144dp,
//                144.dp at 150 using FastOutSlowInEasing // 在第150毫秒的时候，值是144dp, 并且在这一段动画设置先加速再减速的曲线
//                20.dp at 300 // 在第300毫秒的时候，值是20dp,
//
//                durationMillis = 450 // 动画时长 450毫秒
//                delayMillis = 500 // 延时 500毫秒再执行
//
//            })
        )
    })

}

/**
 * 马上变到目标值，没有动画过程
 */
@Composable
private fun AnimationSnapSpecView() {

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
        animatable.animateTo(
            targetValue = if (big.value) { 96.dp } else { 48.dp },
            animationSpec = snap( // 马上变到目标值，没有动画过程
                delayMillis = 1000 // 延时
            )
        )
    })

}

@Composable
private fun AnimationTweenSpecView() {

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
        animatable.animateTo(
            targetValue = if (big.value) { 96.dp } else { 48.dp },
            animationSpec = TweenSpec(
                durationMillis = 300, // 动画时长，默认300毫秒
                delay = 0, // 延时，默认0毫秒
//                easing = LinearEasing // 缓动，动画怎么进行，怎么渐变
                easing = CubicBezierEasing(0.4f, 0.0f, 0.2f, 1.0f) // 自定义动画曲线, 可以搜索一下cubic-bezier在线来确定4个参数，也就是两个点的坐标
                // FastOutSlowInEasing
                // FastOutLinearInEasing
                // LinearEasing // 线性
                // LinearOutSlowInEasing
            )
        )
    })

}

/**
 * 如何配置动画，这里举个例子
 */
@Composable
private fun AnimationSpecView() {

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
        animatable.animateTo(
            targetValue = if (big.value) { 96.dp } else { 48.dp },
            animationSpec = spring(Spring.DampingRatioMediumBouncy) // 配置一个回弹效果的动画
        )
    })

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