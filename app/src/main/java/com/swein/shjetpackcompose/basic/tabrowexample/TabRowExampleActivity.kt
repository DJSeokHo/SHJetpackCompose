package com.swein.shjetpackcompose.basic.tabrowexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.framework.extension.compose.modifier.customTabIndicatorOffset
import com.swein.shjetpackcompose.R
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

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            TextOnlyTab()

            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            IconOnlyTab()

            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            TextAndIconTab()

            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            CustomTab()

            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            CustomTabRow()

            Spacer(modifier = Modifier.padding(vertical = 5.dp))

            CustomScrollableTabRow()
        }


    }

    @Composable
    private fun TextOnlyTab() {

        var tabIndex by remember { mutableStateOf(0) }
        val tabData = listOf(
            "Tab 1",
            "Tab 2",
            "Tab 3",
            "Tab 4"
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

    @Composable
    private fun IconOnlyTab() {

        var tabIndex by remember { mutableStateOf(0) }
        val tabIcon = listOf(
            Icons.Filled.Home,
            Icons.Filled.Search,
            Icons.Filled.Favorite,
            Icons.Filled.Person
        )

        TabRow(
            selectedTabIndex = tabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            backgroundColor = ColorC57644,
            contentColor = ColorC57644
        ) {
            tabIcon.forEachIndexed { index, icon ->

                Tab(
                    selected = tabIndex == index,
                    onClick = {
                        tabIndex = index
                    },
                    modifier = Modifier,
                    enabled = true,
                    icon = {
                        Icon(imageVector = icon, contentDescription = null)
                    },
                    interactionSource = MutableInteractionSource(),
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.LightGray
                )

            }
        }
    }

    @Composable
    private fun TextAndIconTab() {

        var tabIndex by remember { mutableStateOf(0) }
        val tabTextAndIcon = listOf(
            "Home" to Icons.Filled.Home,
            "Search" to Icons.Filled.Search,
            "Favorite" to Icons.Filled.Favorite,
            "Me" to Icons.Filled.Person
        )

        TabRow(
            selectedTabIndex = tabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            backgroundColor = ColorC57644,
            contentColor = ColorC57644
        ) {
            tabTextAndIcon.forEachIndexed { index, pair ->

                Tab(
                    selected = tabIndex == index,
                    onClick = {
                        tabIndex = index
                    },
                    modifier = Modifier,
                    enabled = true,
                    text = {
                        Text(
                            text = pair.first,
                            fontWeight = if (tabIndex == index) {
                                FontWeight.Bold
                            }
                            else {
                                FontWeight.Normal
                            },
                            fontSize = 10.sp
                        )
                    },
                    icon = {
                        Icon(imageVector = pair.second, contentDescription = null)
                    },
                    interactionSource = MutableInteractionSource(),
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.LightGray
                )

            }
        }
    }

    @Composable
    private fun CustomTab() {

        var tabIndex by remember { mutableStateOf(0) }
        val tabTextAndIcon = listOf(
            "Home",
            "Search",
            "Favorite",
            "Me"
        )

        TabRow(
            selectedTabIndex = tabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            backgroundColor = ColorC57644
        ) {
            tabTextAndIcon.forEachIndexed { index, text ->

                val selected = tabIndex == index

                Tab(
                    selected = selected,
                    onClick = {
                        tabIndex = index
                    },
                    enabled = true
                ) {

                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .height(50.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            Modifier
                                .size(30.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Image(
                                painter = painterResource(
                                    id = R.drawable.coding_with_cat_icon
                                ),
                                contentDescription = "",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize().clip(CircleShape)
                            )
                        }

                        Text(
                            text = text,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontSize = if (selected) {
                                12.sp
                            }
                            else {
                               10.sp
                            },
                            color = if (selected) {
                                Color.White
                            } else {
                                Color.DarkGray
                            },
                            fontWeight = if (selected) {
                                FontWeight.Bold
                            }
                            else {
                                FontWeight.Normal
                            }
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun CustomTabRow() {

        var tabIndex by remember { mutableStateOf(0) }
        val tabTextAndIcon = listOf(
            "Home" to Icons.Filled.Home,
            "Search" to Icons.Filled.Search,
            "Favorite" to Icons.Filled.Favorite,
            "Me" to R.drawable.coding_with_cat_icon
        )

        TabRow(
            selectedTabIndex = tabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            backgroundColor = Color.DarkGray,
            divider = {
                TabRowDefaults.Divider(
                    thickness = 1.dp,
                    color = Color.Black
                )
            },
            indicator = {
                TabRowDefaults.Indicator(
//                    modifier = Modifier.tabIndicatorOffset(it[tabIndex]),
                    modifier = Modifier.customTabIndicatorOffset(30.dp, it[tabIndex], 500),
                    height = 3.dp,
                    color = ColorC57644
                )
            }
        ) {
            tabTextAndIcon.forEachIndexed { index, pair ->

                val selected = tabIndex == index

                Tab(
                    selected = selected,
                    onClick = {
                        tabIndex = index
                    },
                    enabled = true
                ) {

                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .height(50.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            Modifier
                                .size(30.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {

                            if (pair.second is Int) {
                                Image(
                                    painter = painterResource(
                                        id = pair.second as Int
                                    ),
                                    contentDescription = "",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.fillMaxSize().clip(CircleShape)
                                )
                            }
                            else if (pair.second is ImageVector) {
                                Icon(
                                    imageVector = pair.second as ImageVector,
                                    contentDescription = null,
                                    tint = if (selected) {
                                        Color.White
                                    }
                                    else {
                                        Color.LightGray
                                    }
                                )
                            }
                        }

                        Text(
                            text = pair.first,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontSize = if (selected) {
                                12.sp
                            }
                            else {
                                10.sp
                            },
                            color = if (selected) {
                                Color.White
                            }
                            else {
                                Color.LightGray
                            },
                            fontWeight = if (selected) {
                                FontWeight.Bold
                            }
                            else {
                                FontWeight.Normal
                            }
                        )
                    }

                }

            }
        }

    }

    @Composable
    private fun CustomScrollableTabRow() {

        var tabIndex by remember { mutableStateOf(0) }
        val tabTextAndIcon = listOf(
            "Tab1" to Icons.Filled.Home,
            "Tab2" to Icons.Filled.Search,
            "Tab3" to Icons.Filled.Favorite,
            "Tab4" to R.drawable.coding_with_cat_icon,
            "Tab5" to Icons.Filled.Home,
            "Tab6" to Icons.Filled.Search,
            "Tab7" to Icons.Filled.Favorite,
            "Tab8" to R.drawable.coding_with_cat_icon,
            "Tab9" to Icons.Filled.Home,
            "Tab10" to Icons.Filled.Search,
            "Tab11" to Icons.Filled.Favorite,
            "Tab12" to R.drawable.coding_with_cat_icon
        )

        ScrollableTabRow(
            selectedTabIndex = tabIndex,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            backgroundColor = Color.DarkGray,
            divider = {
                TabRowDefaults.Divider(
                    thickness = 1.dp,
                    color = Color.Black
                )
            },
            indicator = {
                TabRowDefaults.Indicator(
//                    modifier = Modifier.tabIndicatorOffset(it[tabIndex]),
                    modifier = Modifier.customTabIndicatorOffset(30.dp, it[tabIndex], 500),
                    height = 3.dp,
                    color = ColorC57644
                )
            }
        ) {
            tabTextAndIcon.forEachIndexed { index, pair ->

                val selected = tabIndex == index

                Tab(
                    selected = selected,
                    onClick = {
                        tabIndex = index
                    },
                    enabled = true
                ) {

                    Column(
                        modifier = Modifier
                            .padding(10.dp)
                            .height(50.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            Modifier
                                .size(30.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {

                            if (pair.second is Int) {
                                Image(
                                    painter = painterResource(
                                        id = pair.second as Int
                                    ),
                                    contentDescription = "",
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier.fillMaxSize().clip(CircleShape)
                                )
                            }
                            else if (pair.second is ImageVector) {
                                Icon(
                                    imageVector = pair.second as ImageVector,
                                    contentDescription = null,
                                    tint = if (selected) {
                                        Color.White
                                    }
                                    else {
                                        Color.LightGray
                                    }
                                )
                            }
                        }

                        Text(
                            text = pair.first,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontSize = if (selected) {
                                12.sp
                            }
                            else {
                                10.sp
                            },
                            color = if (selected) {
                                Color.White
                            }
                            else {
                                Color.LightGray
                            },
                            fontWeight = if (selected) {
                                FontWeight.Bold
                            }
                            else {
                                FontWeight.Normal
                            }
                        )
                    }

                }

            }
        }

    }
}