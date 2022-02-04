package com.swein.shjetpackcompose.basic.composetoxml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.shjetpackcompose.R

class ComposeToXMLExampleActivity : AppCompatActivity() {

    private val linearLayout: LinearLayout by lazy {
        findViewById(R.id.linearLayout)
    }

    private val composeView: ComposeView by lazy {
        findViewById(R.id.composeView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compose_to_xmlexample)

        // 静态的占位view进行添加
        composeView.setContent {

            Text(
                text = "text from compose view",
                modifier = Modifier.fillMaxWidth().wrapContentHeight().background(Color.Yellow).padding(20.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.Black
            )
        }

        // 动态添加
        linearLayout.addView(ComposeView(this).apply {
            setContent {

                Text(
                    text = "text from compose view",
                    modifier = Modifier.fillMaxWidth().wrapContentHeight().background(Color.Cyan).padding(20.dp),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black
                )

            }
        })
    }
}