package com.swein.shjetpackcompose.basic.dropdownmenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

/**
fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    properties: PopupProperties = PopupProperties(focusable = true),
    content: @Composable ColumnScope.() -> Unit
)
 */
class DropdownMenuExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Content()

        }
    }


    @Composable
    private fun Content() {

        DropDownMenuButton(
            buttonScope = { text, onClick ->

                DropDownMenuBottom(text, onClick)

            },
            dropDownMenuScope = { expanded, dropDownMenuItems, onDropDownMenuItemSelect, onDismissRequest ->

                DropDownMenu(expanded, dropDownMenuItems, onDropDownMenuItemSelect, onDismissRequest)

            }
        )

    }

    @Composable
    private fun DropDownMenuButton(
        buttonScope: @Composable (text: String, onClick: () -> Unit) -> Unit,
        dropDownMenuScope:
        @Composable
        (expanded: Boolean,
         dropDownMenuItems: List<String>,
         onDropDownMenuItemSelect: (selectedIndex: Int) -> Unit,
         onDismissRequest: () -> Unit
        ) -> Unit
    ) {

        var expanded by remember {
            mutableStateOf(false)
        }

        val items = listOf("Coding", "With", "Cat", "Android", "iOS", "Python")
        var selectedIndex by remember {
            mutableStateOf(0)
        }

        Box {

            buttonScope(
                text = items[selectedIndex],
                onClick = {
                    expanded = true
                }
            )

            dropDownMenuScope(
                expanded = expanded,
                dropDownMenuItems = items,
                onDropDownMenuItemSelect = {
                    selectedIndex = it
                    expanded = false
                },
                onDismissRequest = {
                    expanded = false
                }
            )
        }
    }

    @Composable
    private fun DropDownMenuBottom(text: String, onClick: () -> Unit) {

        TextButton(
            onClick = onClick,
            colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Red, contentColor = Color.White)
        ) {
            Text(
                text = text
            )
        }

    }

    @Composable
    private fun DropDownMenu(
        expanded: Boolean,
        dropDownMenuItems: List<String>,
        onDropDownMenuItemSelect: (selectedIndex: Int) -> Unit,
        onDismissRequest: () -> Unit
    ) {

        DropdownMenu(
            offset = DpOffset(0.dp, 20.dp),
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = Modifier
                .wrapContentSize()
                .background(Color.LightGray)
                .padding(all = 20.dp)
        ) {

            dropDownMenuItems.forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
                    onDropDownMenuItemSelect(index)
                }) {
                    Text(text = text, color = Color.DarkGray)
                }
            }

        }


    }
}