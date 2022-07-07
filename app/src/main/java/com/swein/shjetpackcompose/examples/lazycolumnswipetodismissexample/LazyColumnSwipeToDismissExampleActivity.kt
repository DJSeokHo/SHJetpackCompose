package com.swein.shjetpackcompose.examples.lazycolumnswipetodismissexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.framework.utility.debug.ILog
import kotlin.math.abs

class LazyColumnSwipeToDismissExampleActivity : ComponentActivity() {

    val list = mutableStateListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            ContentView(list)

        }

        list.clear()

        val tempList = mutableListOf<String>()
        for (i in 0 until 100) {
            tempList.add("content $i")
        }

        list.addAll(tempList)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ContentView(list: MutableList<String>) {

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {

        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxSize()
        ) {

            itemsIndexed(
                items = list,
                key = { index: Int, item: String ->
                    item.hashCode()
                }
            ) { index: Int, item: String ->

                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToStart) {
                            list.remove(item)
                        }
                        true
                    }
                )
                
                SwipeToDismiss(
                    state = dismissState,
                    background = {

//                        val color by animateColorAsState(
//                            when (dismissState.targetValue) {
//                                DismissValue.Default -> Color.Transparent
//                                else -> Color.Red
//                            }
//                        )

                        val color = when(dismissState.dismissDirection) {
                            DismissDirection.StartToEnd -> {
                                ILog.debug("???", "1 ${dismissState.offset}")
                                Color.Transparent
                            }
                            DismissDirection.EndToStart -> {
                                ILog.debug("???", "2 ${dismissState.offset}")

                                val r = 1f
                                var g = 1f - (abs(dismissState.offset.value) / 255f) * 0.5f
                                var b = 1f - (abs(dismissState.offset.value) / 255f) * 0.5f

                                if (g <= 0f) {
                                    g = 0f
                                }

                                if (b <= 0f) {
                                    b = 0f
                                }

                                Color(red = r, green = g, blue = b)
                            }
                            else -> {
                                ILog.debug("???", "3 ${dismissState.offset}")
                                Color.Transparent
                            }
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = color)
                                .padding(10.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {

                            Icon(imageVector = Icons.Filled.Delete, null, tint = Color.White)

                        }
                    },
                    directions = setOf(DismissDirection.EndToStart),
                    dismissContent = {

                        ListItemView(item)
                    }
                )

            }
        }
    }
}

@Composable
private fun ListItemView(content: String) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(47.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = content,
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }

}
