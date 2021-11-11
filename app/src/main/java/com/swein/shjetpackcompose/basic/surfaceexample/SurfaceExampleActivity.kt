package com.swein.shjetpackcompose.basic.surfaceexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.swein.framework.compose.ripple.RippleWrapper
import com.swein.shjetpackcompose.R

class SurfaceExampleActivity : ComponentActivity() {
    /*
    @Composable
    fun Surface(
        modifier: Modifier = Modifier,
        shape: Shape = RectangleShape,
        color: Color = MaterialTheme.colors.surface,
        contentColor: Color = contentColorFor(color),
        border: BorderStroke? = null,
        elevation: Dp = 0.dp,
        content: () -> Unit
    ): @Composable Unit

    Material Surface 是 Material Design 的核心隐喻，每个平面都存在于一个特定的高度，这影响了这块平面在视觉上与其他平面的关系以及该平面如何投射阴影。

    可以将 Surface 理解成是一个容器，每个界面元素都基于这个容器，容器可以有不同的高度，可以位于不同的位置。

    Surface 主要负责：

    剪裁：Surface 会根据 shape 属性所描述的形状来裁剪它的子元素。
    高度：Surface 会绘制阴影来表示平面的深度，而这个深度由高度属性 (Elevation) 表示。如果传递的形状是凹进去的，那么在 Android 版本小于 10 的情况下，阴影不会被画出来。
    边框：如果形状有边框，那么它也会被画出来。
    背景：Surface 在 shape 指定的形状上填充颜色。如果颜色是 Colors.surface，将使用 LocalElevationOverlay 中的 ElevationOverlay 来进行叠加--默认情况下，这只会发生在深色主题中。覆盖的颜色取决于这个 Surface 的高度，以及任何父级 Surface 设置的 LocalAbsolutelevation。这可以确保一个 Surface 的叠加高度永远不会比它的祖先低，因为它是所有先前 Surface 的高度的总和
    内容颜色：Surface 使用 contentColor 为这个平面的内容指定一个首选的颜色--这个颜色被文本和图标组件作为默认颜色使用
    如果没有设置 contentColor，这个平面将尝试将其背景颜色与主题 Colors 中定义的颜色相匹配，并返回相应的内容颜色。例如，如果这个平面的颜色是 Colors.surface，contentColor 将被设置为 Colors.onSurface。如果颜色不是主题调色板的一部分，contentColor 将保持这个 Surface 上面设置的相同值
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Column {
                SurfaceExample1()

                SurfaceExample2()
            }

        }
    }

    @Composable
    private fun SurfaceExample1() {

        val context = LocalContext.current

        Surface(
            shape = RoundedCornerShape(8.dp),
            elevation = 5.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .clickable(
                        interactionSource = RippleWrapper.CreateMutableInteractionSource(),
                        indication = RippleWrapper.CreateIndication(true, Color.Red),
                        onClick = {
                            Toast
                                .makeText(context, "surface example 1", Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                    .padding(15.dp)
            ) {

                Text(
                    buildAnnotatedString {
                        append("Coding with cat ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)
                        ) {
                            append("is good")
                        }
                    }
                )

                Spacer(modifier = Modifier.padding(vertical = 5.dp))

                Text(
                    buildAnnotatedString {
                        append("Have you subscribe to my Channel? ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("\nNo? -_- ......")
                        }
                    }
                )
            }
        }

    }

    @Composable
    private fun SurfaceExample2() {

        val context = LocalContext.current

        Surface(
            shape = RoundedCornerShape(8.dp),
            elevation = 5.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Row(
                modifier = Modifier
                    .clickable(
                        interactionSource = RippleWrapper.CreateMutableInteractionSource(),
                        indication = RippleWrapper.CreateIndication(true, Color.Cyan),
                        onClick = {
                            Toast
                                .makeText(context, "surface example 2", Toast.LENGTH_SHORT)
                                .show()
                        }
                    )
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.coding_with_cat_icon),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(130.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.padding(10.dp))

                Column(
                    modifier = Modifier.padding(end = 10.dp).fillMaxWidth()
                ) {

                    Text(
                        buildAnnotatedString {
                            append("Coding with cat ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)
                            ) {
                                append("\nis good")
                            }
                        }
                    )

                    Spacer(modifier = Modifier.padding(vertical = 5.dp))

                    Text(
                        buildAnnotatedString {
                            append("Have you subscribe to my Channel? ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("\nNo? -_- ......")
                            }
                        }
                    )
                }

            }

        }

    }


}