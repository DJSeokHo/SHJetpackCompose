package com.swein.shjetpackcompose.examples.marginandpaddingexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MarginAndPaddingExampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MarginAndPaddingExampleView.ActivityContentView()
        }
    }

}


object MarginAndPaddingExampleView {

    @Composable
    fun ActivityContentView() {

//        TestExample()
//        BoxAndTextCenterExample()
        BoxAndTextMarginAndPaddingExample()
    }

    @Composable
    private fun TestExample() {

        Text(
            text = "Hello",
            modifier = Modifier
//                .padding(15.dp) // 先 padding 再加背景 看起来像传统的 margin
                .background(Color.Gray)
                .padding(15.dp) // 先加背景再 padding 看起来像传统的 padding

        )

    }

    @Composable
    private fun BoxAndTextCenterExample() {

        Box(
            modifier = Modifier.size(200.dp, 100.dp).background(Color.Gray)
        ) {
            // align() 需要box之类的容器, 如果父亲是Surface，不会有align
            // align() 在这里作用类似于 layout_gravity

            // wrapContentHeight 和 wrapContentWidth 类似于 gravity
            Text(
                text = "Hello",
                modifier = Modifier.background(Color.Red).size(100.dp, 50.dp).align(Alignment.Center)
                    .wrapContentHeight(CenterVertically).wrapContentWidth(CenterHorizontally)
            )
        }
    }

    /**
     * padding 是 padding 还是 margin 需要 background 才能区分
     * background之前就是设置 padding 就是margin，之后就是padding
     * 如果没有background，那么padding 都是在设置margin
     */
    @Composable
    private fun BoxAndTextMarginAndPaddingExample() {

        Box(
            modifier = Modifier.size(200.dp, 100.dp).background(Color.Gray)

            // 先背景再padding 看起来就像是内部的Text设置了Margin
//            modifier = Modifier.size(200.dp, 100.dp).background(Color.Gray).padding(start = 30.dp),

            // 先padding再背景 看起来就像是Box设置了Margin
//            modifier = Modifier.size(200.dp, 100.dp).padding(start = 30.dp).background(Color.Gray),

            // 以上两种情况的合体
//            modifier = Modifier.size(200.dp, 100.dp).padding(start = 30.dp).background(Color.Gray).padding(start = 30.dp)
        ) {

            Text(
                text = "Hello",
                // 先 padding 再背景 看起来就像是 Text 设置了 Margin
                modifier = Modifier.padding(start = 30.dp).background(Color.Red).size(100.dp, 50.dp),

                // 先背景再 padding 看起来就像是 Text 设置了 Padding
//                modifier = Modifier.background(Color.Red).padding(start = 30.dp).size(100.dp, 50.dp),
            )
        }
    }
}

@Preview(showBackground = true, name = "MarginAndPaddingExampleActivityPreview")
@Composable
fun MarginAndPaddingExampleActivityPreview() {

    MarginAndPaddingExampleView.ActivityContentView()

}