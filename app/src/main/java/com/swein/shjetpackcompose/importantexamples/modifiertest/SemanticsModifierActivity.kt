package com.swein.shjetpackcompose.importantexamples.modifiertest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.OnRemeasuredModifier
import androidx.compose.ui.semantics.SemanticsModifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.swein.shjetpackcompose.R

class SemanticsModifierActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            /**
             * SemanticsModifier 提供一个语义树,
             * 主要应用在无障碍方面, 但是手机需要开启辅助功能中的talkback
             * 还有开发测试方面
             *
             * SemanticsModifier同理，也是针对他右边(下方)的那个LayoutModifier作用的。如果右边(下方)没有了，那就是针对那个原始控件
             */

            Column {
                Text(text = "123") // 无障碍时会读出这个文字内容，因为Text是一个有语义组件
                Box(
                    // 无障碍时不会读这个空色块内容，因为Box是一个无语义组件
                    modifier = Modifier
                        .width(100.dp)
                        .height(60.dp)
                        .background(Color.Magenta)
                        .semantics(true) {
                            // 默认是false,
                            // 如果true, 那么这个组件将合并其内部组件的无障碍内容，并且不会被其父组件合并掉


                            // 加了这个的话
                            // 无障碍时会读出这个文字内容，因为Box被添加了语义，此时是一个有语义组件
                            contentDescription = "有颜色的大方块"
                        }
                )

                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(60.dp)
                        .background(Color.Magenta)
                        .clearAndSetSemantics {
                            contentDescription = "有颜色的大方块"
                        }
                ) {
                    Text(text = "方块内部")
                }
            }
        }
    }
}