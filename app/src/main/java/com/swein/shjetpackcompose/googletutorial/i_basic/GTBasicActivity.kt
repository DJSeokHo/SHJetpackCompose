package com.swein.shjetpackcompose.googletutorial.i_basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.swein.shjetpackcompose.application.ui.theme.SHJetpackComposeTheme
import com.swein.shjetpackcompose.googletutorial.i_basic.model.SampleData
import com.swein.shjetpackcompose.googletutorial.i_basic.view.GTBasicView

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