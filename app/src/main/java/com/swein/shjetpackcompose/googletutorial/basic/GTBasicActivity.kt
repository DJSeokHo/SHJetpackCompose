package com.swein.shjetpackcompose.googletutorial.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.swein.shjetpackcompose.application.ui.theme.SHJetpackComposeTheme
import com.swein.shjetpackcompose.googletutorial.basic.model.Message
import com.swein.shjetpackcompose.googletutorial.basic.model.SampleData
import com.swein.shjetpackcompose.googletutorial.basic.view.GTBasicView

class GTBasicActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            GTBasicView.MessageCard("Seok Ho")
            SHJetpackComposeTheme {
//                GTBasicView.MessageCard(
//                    message = Message("Coding with cat", "Hey, subscribe to my channel\nAndroid tutorial step by step!")
//                )

                GTBasicView.Conversation(SampleData.conversationSample)
            }
        }
    }
}