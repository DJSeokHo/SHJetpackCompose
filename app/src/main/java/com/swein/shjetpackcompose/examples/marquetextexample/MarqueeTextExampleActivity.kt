package com.swein.shjetpackcompose.examples.marquetextexample

import android.graphics.Typeface
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

class MarqueeTextExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            ContentView()

        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ContentView() {

//    val startMarquee = remember {
//        mutableStateOf(false)
//    }

    val localUriHandler = LocalUriHandler.current

    Surface(
        color = Color.White
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                modifier = Modifier.basicMarquee(
                    iterations = Int.MAX_VALUE,
                    animationMode = MarqueeAnimationMode.Immediately,
                    delayMillis = 0,
                    spacing = MarqueeSpacing(10.dp),
                    velocity = 50.dp
                ),
                text = "Compose 1.4.0 has Marquee! Compose 1.4.0 has Marquee! Compose 1.4.0 has Marquee! Compose 1.4.0 has Marquee!"
            )

            Spacer(modifier = Modifier.padding(vertical = 20.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black)
                    .basicMarquee(
                        iterations = Int.MAX_VALUE,
                        animationMode = MarqueeAnimationMode.Immediately,
                        delayMillis = 0,
                        spacing = MarqueeSpacing(30.dp),
                        velocity = 50.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Default.Home,
                    contentDescription = ""
                )

                Spacer(modifier = Modifier.padding(horizontal = 5.dp))

                Text(
                    text = "Hello! ",
                )

                Spacer(modifier = Modifier.padding(horizontal = 5.dp))

                Button(onClick = {

                    localUriHandler.openUri("https://www.youtube.com/@codingwithcat")

                }) {
                    Text("Coding with cat")
                }

                Spacer(modifier = Modifier.padding(horizontal = 5.dp))

                Text("Compose 1.4.0 has Marquee!")
            }


//            MarqueeText(
//                startMarquee = startMarquee.value, text = "我是头部😀,这是一个超长文本内容,你好大魔头,就算失败也要摆出豪迈的姿态,我是尾巴🙈",
//                textColor = Color.Red,
//                typeFaceStyle = Typeface.BOLD,
//                fontFamily = FontFamily.Monospace
//            )
//
//            Button(
//                onClick = {
//                    startMarquee.value = true
//                }, modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentSize(align = Alignment.BottomCenter)
//                    .padding(16.dp)
//            ) {
//                Text(text = "测试一下跑马灯效果")
//            }
        }

    }
}

@Composable
fun MarqueeText(
    modifier: Modifier = Modifier,
    startMarquee: Boolean,
    text: String,
    blankText:String = "                ",
    textColor: Color = Color.Unspecified,
    fontSize: Float = 50f,
    typeFaceStyle: Int = Typeface.NORMAL,
    //内置的一些字体
    fontFamily: FontFamily = FontFamily.Default
) {
    val transition = rememberInfiniteTransition()
    val animProgress by transition.animateFloat(
        initialValue = 1.2F,
        targetValue = 0F,
        animationSpec = infiniteRepeatable(
            tween(
                20000,
                easing = LinearEasing
            ), RepeatMode.Restart
        )
    )
    val paint = android.graphics.Paint().apply {

        isDither = true
        isAntiAlias = true
        textAlign = android.graphics.Paint.Align.LEFT
        textSize = fontSize

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            color = android.graphics.Color.argb(textColor.alpha, textColor.red, textColor.green, textColor.blue)
//        }
//        else {
//            color = android.graphics.Color.rgb(textColor.red.toInt(), textColor.green.toInt(), textColor.blue.toInt())
//            alpha = (textColor.alpha * 255F).toInt()
//        }

        color = android.graphics.Color.argb(textColor.alpha, textColor.red, textColor.green, textColor.blue)

        typeface = when (fontFamily) {
            FontFamily.Default -> Typeface.create(Typeface.DEFAULT, typeFaceStyle)
            FontFamily.Cursive -> Typeface.create(FontFamily.Cursive.name, typeFaceStyle)
            FontFamily.Serif -> Typeface.create(FontFamily.Serif.name, typeFaceStyle)
            FontFamily.SansSerif -> Typeface.create(FontFamily.SansSerif.name, typeFaceStyle)
            FontFamily.Monospace -> Typeface.create(FontFamily.Monospace.name, typeFaceStyle)
            else -> null
        }
    }

    val defaultContentWidth = paint.measureText(text)
    //增加头尾留白间距
    val marqueeText = text + blankText
    //测量跑马灯的总长度
    val marqueeWidth = paint.measureText(marqueeText)
    //用于纠正首次区间和后面循环到头部的时候短暂停顿的标志位
    var firstAnimTotalProgress = -1F

    Canvas(
        modifier = modifier.fillMaxWidth(),
        onDraw = {
            drawIntoCanvas {
                if (startMarquee && defaultContentWidth > size.width) {
                    if(firstAnimTotalProgress == -1F) {
                        //首次纠正：区间取值问题，[0F-1F] 实际用的是 (0F-1F)
                        firstAnimTotalProgress = animProgress
                    }
                    var fixProgress = (animProgress * 1F / firstAnimTotalProgress * 1F)
                    if(fixProgress >=1F){
                        //处理非首次大于1F的时候,用于短暂停顿的效果
                        fixProgress = 1F
                    }
                    val offsetX: Float = fixProgress * marqueeWidth
                    if (offsetX <= size.width) {
                        //左侧滚动出屏幕的内容,从右侧滚动进屏幕的内容
                        it.nativeCanvas.drawText(marqueeText, offsetX, center.y, paint)
                    }
                    //内容滚动出屏幕
                    it.nativeCanvas.drawText(
                        marqueeText,
                        (offsetX - marqueeWidth),
                        center.y,
                        paint
                    )
                } else {
                    it.nativeCanvas.drawText(text, 0F, center.y, paint)
                }
            }
        })
}