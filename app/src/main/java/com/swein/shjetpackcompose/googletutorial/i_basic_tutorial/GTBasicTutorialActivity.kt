package com.swein.shjetpackcompose.googletutorial.i_basic_tutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.swein.shjetpackcompose.application.ui.theme.SHJetpackComposeTheme
import com.swein.shjetpackcompose.googletutorial.i_basic_tutorial.model.SampleData
import com.swein.shjetpackcompose.googletutorial.i_basic_tutorial.view.GTBasicTutorialView

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