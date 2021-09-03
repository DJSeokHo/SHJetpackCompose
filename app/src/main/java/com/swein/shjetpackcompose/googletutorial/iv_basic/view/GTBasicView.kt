package com.swein.shjetpackcompose.googletutorial.iv_basic.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.swein.framework.utility.debug.ILog
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.swein.shjetpackcompose.application.ui.theme.SHJetpackComposeTheme

object GTBasicView {

    private const val TAG = "GTBasicView"

    @Composable
    fun Content() {
        
        SHJetpackComposeTheme {

            FullScreenList()

        }
    }

    @Composable
    fun FullScreenList(list: List<String> = List(1000) { "$it" }) {

        Surface(color = Color.Yellow) {

            Column(modifier = Modifier.fillMaxHeight()) {

                LazyColumn(modifier = Modifier.weight(1f)) {

                    items(items = list) { item ->

//                        ListItem(content = item)
                        ListItemWithClick(content = item)
                        Divider(color = Color.LightGray, thickness = 0.8.dp)
                    }

                }

                ButtonWrapper()

            }

        }

    }

    @Composable
    fun ListItemWithClick(content: String) {

//        val isSelected = remember {
//            mutableStateOf(false)
//        }
//
//        val backgroundColor = animateColorAsState(if (isSelected.value) { Color.Red } else { Color.Transparent })



        /*
            Note: Make sure these imports are included in your file otherwise delegate properties (the by keyword) won't work:
            import androidx.compose.runtime.getValue
            import androidx.compose.runtime.setValue
         */
        var isSelected by remember {
            mutableStateOf(false)
        }
        val backgroundColor by animateColorAsState(if (isSelected) { Color.Red } else { Color.Transparent })

        Surface(color = Color.Transparent) {
            Text(
                text = "List item $content",
                modifier = Modifier
                    .padding(24.dp)
                    .background(color = backgroundColor)
                    .clickable(onClick = {
                        isSelected = !isSelected
                    })
            )
        }
    }

    @Composable
    fun ListItem(content: String) {
        Surface(color = Color.Transparent) {
            Text(
                text = "List item $content",
                modifier = Modifier.padding(24.dp),
                style = MaterialTheme.typography.h1
            )
        }
    }

    @Composable
    fun ButtonWrapper() {

        val count = remember {
            mutableStateOf(0)
        }

        Surface(color = Color.Cyan) {
            ButtonWithStateHoisting(value = count.value) {
                count.value++
            }
        }
    }

    @Composable
    fun ButtonWithStateHoisting(value: Int, onButtonClick: () -> Unit) {

        Button(
            onClick = onButtonClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (value > 5) { Color.Red } else { Color.White },
                contentColor = if (value > 5) { Color.White } else { Color.Black },
            ),
            modifier = Modifier.padding(24.dp)
        ) {
            Text(text = "button $value")
        }
    }

}

@Preview(showBackground = true, name = "Text preview")
@Composable
private fun DefaultPreview() {
    GTBasicView.Content()
}