package com.swein.shjetpackcompose.examples.viewpagerandlistexample

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import com.swein.shjetpackcompose.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabRowPagerListControllerView(
    listFood: MutableList<String>,
    listDrink: MutableList<String>,
    onReload: (type: String) -> Unit,
    onLoadMore: (type: String) -> Unit
) {

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TabRowView(pagerState)
        PagerView(pagerState, listFood, listDrink, onLoadMore = onLoadMore)
    }

    LaunchedEffect(key1 = null, block = {

        onReload("food")
        onReload("drink")

    })
}


@OptIn(ExperimentalPagerApi::class)
@Composable
private fun TabRowView(
    pagerState: PagerState
) {

    val tabTextAndIcon = listOf(
        "food" to 0,
        "drink" to R.drawable.coding_with_cat_icon,
    )

    val coroutineScope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        backgroundColor = Color.DarkGray,
        contentColor = Color.DarkGray,
        divider = {
            TabRowDefaults.Divider(
                thickness = 3.dp,
                color = Color.Black
            )
        },
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 3.dp,
                color = Color.Yellow
            )
        }
    ) {
        tabTextAndIcon.forEachIndexed { index, pair ->

            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                enabled = true
            ) {

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(50.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    if (pair.second != 0) {
                        Image(
                            painter = painterResource(
                                id = pair.second
                            ),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(20.dp)
                                .clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.padding(vertical = 2.dp))
                    }

                    Text(
                        text = pair.first,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = if (pagerState.currentPage == index) {
                            16.sp
                        } else {
                            12.sp
                        },
                        color = if (pagerState.currentPage == index) {
                            Color.White
                        } else {
                            Color.LightGray
                        },
                        fontWeight = if (pagerState.currentPage == index) {
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

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
private fun PagerView(
    pagerState: PagerState,
    listFood: MutableList<String>,
    listDrink: MutableList<String>,
    onLoadMore: (type: String) -> Unit
) {

    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            HorizontalPager(
                count = 2,
                state = pagerState
            ) { page ->

                when (page) {
                    0 -> {
                        FoodListView(listFood, onLoadMore = {
                            onLoadMore("food")
                        })
                    }

                    1 -> {
                        DrinkListView(listDrink, onLoadMore = {
                            onLoadMore("drink")
                        })
                    }

                }
            }
        }
    }

}

@Composable
private fun FoodListView(list: MutableList<String>, onLoadMore: () -> Unit) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            LazyColumn(
                contentPadding = PaddingValues(
                    horizontal = 2.dp
                ),
                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxSize()
            ) {

                itemsIndexed(
                    items = list,
                    key = { _: Int, item: String ->
                        item.hashCode()
                    }
                ) { index, item ->

                    ListItemView(item)

                    if (index == list.size - 1) {

                        LaunchedEffect(key1 = list.size, block = {
                            // use LaunchedEffect to make sure that load more only once
                            onLoadMore()
                        })
                    }
                }
            }

        }
    }

}

@Composable
private fun DrinkListView(list: MutableList<String>, onLoadMore: () -> Unit) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            LazyColumn(
                contentPadding = PaddingValues(
                    horizontal = 2.dp
                ),
                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxSize()
            ) {

                itemsIndexed(
                    items = list,
                    key = { _: Int, item: String ->
                        item.hashCode()
                    }
                ) { index, item ->

                    ListItemView(item)

                    if (index == list.size - 1) {

                        LaunchedEffect(key1 = list.size, block = {
                            // use LaunchedEffect to make sure that load more only once
                            onLoadMore()
                        })
                    }
                }
            }

        }
    }

}

@Composable
private fun ListItemView(
    content: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(Color.White)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = content,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }

        Divider(color = Color.Black, thickness = 0.8.dp)
    }
}