package com.swein.shjetpackcompose.basic.tabrowexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.swein.shjetpackcompose.application.ui.theme.ColorC57644
import com.swein.shjetpackcompose.application.ui.theme.SHJetpackComposeTheme

class TabRowExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            SHJetpackComposeTheme {
                ContentView()
            }

        }
    }

    @Composable
    private fun ContentView() {

        TextOnlyTab()

    }

    @Composable
    private fun TextOnlyTab() {

        var tabIndex by remember { mutableStateOf(0) }
        val tabData = listOf(
            "Tab 1",
            "Tab 2",
            "Tab 3",
            "Tab 4",
        )

        TabRow(
            selectedTabIndex = tabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            backgroundColor = ColorC57644,
            contentColor = ColorC57644
        ) {
            tabData.forEachIndexed { index, text ->

                Tab(
                    selected = tabIndex == index,
                    onClick = {
                        tabIndex = index
                    },
                    modifier = Modifier,
                    enabled = true,
                    text = {
                        Text(
                            text = text,
                            fontWeight = if (tabIndex == index) {
                                FontWeight.Bold
                            }
                            else {
                                FontWeight.Normal
                            }
                        )
                    },
                    interactionSource = MutableInteractionSource(),
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.LightGray
                )

            }
        }
    }
}