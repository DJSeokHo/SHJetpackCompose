package com.swein.shjetpackcompose.googletutorial.i_basic_tutorial.view

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.application.ui.theme.SHJetpackComposeTheme
import com.swein.shjetpackcompose.googletutorial.i_basic_tutorial.model.Message
import com.swein.shjetpackcompose.googletutorial.i_basic_tutorial.model.SampleData
import com.swein.shjetpackcompose.googletutorial.i_basic_tutorial.view.GTBasicTutorialView.Conversation

object GTBasicTutorialView {

    @Composable
    fun MessageCard(name: String) {
        Text(text = "Hello $name")
    }

    @Composable
    fun MessageCard(message: Message) {

        Row(modifier = Modifier.padding(all = 8.dp)) {

            Image(
                painter = painterResource(id = R.drawable.coding_with_cat_icon),
                contentDescription = "Test Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
            )
            
            Spacer(modifier = Modifier.width(8.dp))

            // We keep track if the message is expanded or not in this
            // variable
            var isExpanded by remember {
                mutableStateOf(false)
            }

            // surfaceColor will be updated gradually from one color to the other
            val surfaceColor: Color by animateColorAsState(
                if (isExpanded) {
                    MaterialTheme.colors.primary
                }
                else {
                    MaterialTheme.colors.surface
                },
            )

            // We toggle the isExpanded variable when we click on this Column
            Column(
                modifier = Modifier.clickable {
                    isExpanded = !isExpanded
                }
            ) {

                Text(
                    text = message.author,
                    color = MaterialTheme.colors.secondaryVariant,
                    style = MaterialTheme.typography.subtitle2
                )

                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    shape = MaterialTheme.shapes.medium,
                    elevation = 1.dp,
                    color = surfaceColor,
                    // animateContentSize will change the Surface size gradually
                    modifier = Modifier.animateContentSize().padding(1.dp)
                ) {

                    Text(
                        text = message.body,
                        modifier = Modifier.padding(all = 4.dp),
                        // If the message is expanded, we display all its content
                        // otherwise we only display the first line
                        maxLines = if (isExpanded) {
                            Int.MAX_VALUE
                        }
                        else {
                            1
                        },
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }

    @Composable
    fun Conversation(messages: List<Message>) {
        LazyColumn {
            items(messages) { message ->
                MessageCard(message)
            }
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    SHJetpackComposeTheme {
        Conversation(SampleData.conversationSample)
    }
}

@Preview
@Composable
private fun MessageCardPreview() {
    GTBasicTutorialView.MessageCard("Seok Ho")
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
private fun MessageObjectCardPreview() {
    SHJetpackComposeTheme {
        GTBasicTutorialView.MessageCard(
            message = Message("Coding with cat", "Hey, subscribe to my channel\nAndroid tutorial step by step!")
        )
    }
}