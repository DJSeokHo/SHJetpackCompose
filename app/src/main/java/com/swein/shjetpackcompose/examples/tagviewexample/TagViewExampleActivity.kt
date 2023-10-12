package com.swein.shjetpackcompose.examples.tagviewexample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class TagViewExampleViewModel: ViewModel() {

    val tagList = mutableStateListOf<String>()

    fun addTag(tag: String) {
        tagList.add(tag)
    }

    fun removeTag(tag: String) {
        tagList.remove(tag)
    }
}

class TagViewExampleActivity : AppCompatActivity() {

    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ContentView()

        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ContentView(
    viewModel: TagViewExampleViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    val tag = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {

            BasicTextField(
                value = tag.value,
                onValueChange = {
                    tag.value = it
                },
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .border(1.dp, Color.Black, CircleShape)
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                maxLines = 1,
                decorationBox = { innerTextField ->

                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "input tag",
                            color = Color.LightGray,
                            fontSize = 14.sp
                        )
                        innerTextField()
                    }
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 14.sp
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {

                        if (tag.value == "") {
                            return@KeyboardActions
                        }

                        viewModel.addTag(tag.value)
                        keyboardController?.hide()
                        tag.value = ""
                    }
                )
            )
        }

        FlowRow(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = 10.dp)
        ) {

//            for (i in 0 until 80) {
//                TagItem(generateRandomString())
//            }

            for (item in viewModel.tagList) {
                TagItem(item, onRemoveClick = {
                    viewModel.removeTag(it)
                })
            }
        }
    }

}

@Composable
private fun TagItem(text: String, onRemoveClick: (text: String) -> Unit) {

    Row(
        modifier = Modifier
            .padding(top = 5.dp)
            .padding(horizontal = 5.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(Color.Black)
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text,
            color = Color.White
        )

        Spacer(modifier = Modifier.padding(horizontal = 4.dp))

        Icon(
            Icons.Filled.Close,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(10.dp)
                .clickable {
                    onRemoveClick(text)
                }
        )
    }
}

fun generateRandomString(): String {
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    val randomLength = Random.nextInt(5, 25) // Random length between 5 and 40

    return (1..randomLength)
        .map { charPool[Random.nextInt(0, charPool.size)] }
        .joinToString("")
}