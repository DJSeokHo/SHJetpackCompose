package com.swein.shjetpackcompose.googletutorial.vi_layout.view.scrollinglist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import kotlinx.coroutines.launch

object GTLayoutScrollingListView {

    @Composable
    fun ScrollingList() {
        val listSize = 100
        // We save the scrolling position with this state
        val scrollState = rememberLazyListState()
        // We save the coroutine scope where our animated scroll will be executed
        val coroutineScope = rememberCoroutineScope()

        Column {
            Row {
                Button(onClick = {

                    coroutineScope.launch {

                        // 0 is the first item index
                        scrollState.animateScrollToItem(0)
                    }

                }) {
                    Text(text = "Scroll to top")
                }

                Button(onClick = {

                    coroutineScope.launch {

                        // listSize - 1 is the last index of the list
                        scrollState.animateScrollToItem(listSize - 1)
                    }

                }) {
                    Text(text = "Scroll to end")
                }
            }

            LazyColumn(state = scrollState) {
                items(listSize) {
                    ImageListItem(it)
                }
            }
        }
    }


    @Composable
    fun ImageList() {
        // We save the scrolling position with this state
        val scrollState = rememberLazyListState()

        LazyColumn(state = scrollState) {
            items(100) {
                ImageListItem(it)
            }
        }
    }

    @Composable
    fun ImageListItem(index: Int) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            Image(
                painter = rememberImagePainter(
                    data = "https://developer.android.com/images/brand/Android_Robot.png"
                ),
                contentDescription = "Android Logo",
                modifier = Modifier.size(50.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text("Item #$index", style = MaterialTheme.typography.subtitle1)
        }
    }

    @Composable
    fun ColumnWithScrollable() {

        // We save the scrolling position with this state that can also
        // be used to programmatically scroll the list
        val scrollState = rememberScrollState()

        Column(Modifier.verticalScroll(scrollState)) {
            repeat(100) {
                Text("Item #$it")
            }
        }
    }

    @Composable
    fun LazyList() {
        // We save the scrolling position with this state that can also
        // be used to programmatically scroll the list
        val scrollState = rememberLazyListState()

        LazyColumn(state = scrollState) {
            items(100) {
                Text("Item #$it")
            }
        }
    }
}

@Preview(showBackground = true, name = "layout scrolling list")
@Composable
fun DefaultGTLayoutScrollingList() {
    GTLayoutScrollingListView.ScrollingList()
}