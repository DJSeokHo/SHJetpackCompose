package com.swein.shjetpackcompose.googletutorial.ibasictutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.swein.shjetpackcompose.application.ui.theme.SHJetpackComposeTheme
import com.swein.shjetpackcompose.googletutorial.ibasictutorial.model.SampleData
import com.swein.shjetpackcompose.googletutorial.ibasictutorial.view.GTBasicTutorialView

class GTBasicTutorialActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            GTBasicView.MessageCard("Seok Ho")
            SHJetpackComposeTheme {
//                GTBasicView.MessageCard(
//                    message = Message("Coding with cat", "Hey, subscribe to my channel\nAndroid tutorial step by step!")
//                )

                GTBasicTutorialView.Conversation(SampleData.conversationSample)
            }
        }
    }
}