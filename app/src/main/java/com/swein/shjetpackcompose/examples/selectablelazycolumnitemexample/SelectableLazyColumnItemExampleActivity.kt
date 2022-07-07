package com.swein.shjetpackcompose.examples.selectablelazycolumnitemexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SelectableLazyColumnItemExampleActivity : ComponentActivity() {

    val list = mutableStateListOf<SelectableItemModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            ContentView(list)

        }

        list.clear()

        val tempList = mutableListOf<SelectableItemModel>()
        for (i in 0 until 100) {
            tempList.add(
                SelectableItemModel(
                    "content $i",
                    mutableStateOf(false)
                )
            )
        }

        list.addAll(tempList)
    }

}

@Composable
private fun ContentView(list: MutableList<SelectableItemModel>) {

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

            items(
                items = list
            ) { item ->
                ListItemView(item)
            }
        }

    }

}

@Composable
private fun ListItemView(model: SelectableItemModel) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 6.dp, horizontal = 16.dp)
    ) {

        Text(
            text = model.content,
            fontSize = if (model.selected.value) {
                30.sp
            }
            else {
                18.sp
            },
            color = if (model.selected.value) {
                Color.Black
            }
            else {
                Color.Gray
            },
            fontWeight = if (model.selected.value) {
                FontWeight.Bold
            }
            else {
                FontWeight.Normal
            },
            modifier = Modifier
                .align(Alignment.CenterStart)
        )

        Checkbox(
            checked = model.selected.value,
            onCheckedChange = {
                model.toggle()
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Black,
                uncheckedColor = Color.Gray
            ),
            modifier = Modifier
                .align(Alignment.CenterEnd)
        )
    }

}

data class SelectableItemModel(
    var content: String,
    var selected: MutableState<Boolean>
) {

    fun toggle() {
        selected.value = !selected.value
    }

}