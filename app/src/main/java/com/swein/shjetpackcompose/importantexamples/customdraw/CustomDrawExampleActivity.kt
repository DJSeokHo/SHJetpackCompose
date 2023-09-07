package com.swein.shjetpackcompose.importantexamples.customdraw

import android.graphics.Camera
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.rotateRad
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.swein.shjetpackcompose.R
import kotlin.math.roundToInt

class xCustomDrawExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            /*
            自定义绘制就用以下三个函数
             */
//            Modifier.drawWithContent { // 用于控制绘制顺序
//                drawContent() // 绘制原有的内容
//            }

//            Modifier.drawBehind {
//
//            }
            Canvas(modifier = Modifier.size(80.dp), onDraw = {})
        }
    }
}

@Preview
@Composable
private fun Custom3DRotateImage() {

    val image = ImageBitmap.imageResource(id = R.drawable.coding_with_cat_icon)
    val paint = remember {
        mutableStateOf(Paint())
    }
    val camera = remember {
        mutableStateOf(Camera())
    }.apply {
        value.rotateX(45f)
    }

    val rotationAnimatable = remember {
        mutableStateOf(androidx.compose.animation.core.Animatable(0f))
    }
    
    LaunchedEffect(key1 = Unit, block = {
        rotationAnimatable.value.animateTo(360f, infiniteRepeatable(tween(2000)))
    })

    Box(
        modifier = Modifier
            .padding(30.dp)
    ) {

        Canvas(
            modifier = Modifier
                .size(100.dp),
//                .graphicsLayer {
//                    // 做3维旋转，不能用这个
//                    rotationX = 45f
//
//                    rotationY = 45f
//
//                    // 这样连续旋转会得到错误的结果
//                    // 因为这里旋转的是坐标轴，而不是图像本身。
//                    // X 转了 45， 坐标轴都变了，在此基础上再旋转Y，肯定结果不符合预期。
//                }
            onDraw = {

                drawIntoCanvas {

                    // 先把原点移动到图片中心
                    it.translate(size.width * 0.5f, size.height * 0.5f)

                    camera.value.save()
                    camera.value.rotateX(rotationAnimatable.value.value)
                    // 做3维旋转
                    camera.value.applyToCanvas(it.nativeCanvas)
                    camera.value.restore()

                    it.translate(-size.width * 0.5f, -size.height * 0.5f)

                    it.drawImageRect(
                        image = image,
                        dstSize = IntSize(size.width.roundToInt(), size.height.roundToInt()),
                        paint = paint.value
                    )
                }

//                drawImage(
//                    image = image,
//                    dstSize = IntSize(size.width.roundToInt(), size.height.roundToInt()), // 目标尺寸
//    //            dstOffset = // 目标偏移
//                )
            }
        )

    }

}

@Preview
@Composable
private fun CustomImage() {

    val image = ImageBitmap.imageResource(id = R.drawable.coding_with_cat_icon)

    Box(
        modifier = Modifier
            .padding(30.dp)
    ) {

        Canvas(modifier = Modifier.size(100.dp), onDraw = {

//            rotateRad() // 弧度 radian

            // 旋转 只能2D平面旋转
            rotate(30f) {
                drawImage(
                    image = image,
                    dstSize = IntSize(size.width.roundToInt(), size.height.roundToInt()), // 目标尺寸
//            dstOffset = // 目标偏移
                )
            }
            // rotate 以外的部分是不会旋转的，这是优点，比起老的view系统，不会互相影响。

        })

    }

}

@Preview
@Composable
fun CustomText() {

    Text(
        text = "Coding with cat",
//        modifier = Modifier.drawBehind {
//            drawRect(Color.White)
//        }
        modifier = Modifier.drawWithContent {
            drawRect(Color.White)
            drawContent()
            drawLine(
                Color.Gray,
                Offset(0f, size.height * 0.5f),
                Offset(size.width, size.height * 0.5f),
                2.dp.toPx()
            )
        }
    )
}