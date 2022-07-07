package com.swein.shjetpackcompose.basic.tabrowpagerexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.application.ui.theme.Color6868AD
import kotlinx.coroutines.launch


class TabRowPagerExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            ContentView()

        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun ContentView() {

        val pagerState = rememberPagerState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            TabRow(pagerState)
            PagerContent(pagerState)
        }

    }


    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun TabRow(pagerState: PagerState) {

        val tabTextAndIcon = listOf(
            "tab 1" to R.drawable.coding_with_cat_icon,
            "tab 2" to R.drawable.coding_with_cat_icon,
            "tab 3" to R.drawable.coding_with_cat_icon,
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
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Box(
                            Modifier
                                .size(30.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {

                            Image(
                                painter = painterResource(
                                    id = pair.second
                                ),
                                contentDescription = "",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape)
                            )
                        }

                        Text(
                            text = pair.first,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontSize = if (pagerState.currentPage == index) {
                                12.sp
                            }
                            else {
                                10.sp
                            },
                            color = if (pagerState.currentPage == index) {
                                Color.White
                            }
                            else {
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
    private fun PagerContent(pagerState: PagerState) {

        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                HorizontalPager(
                    count = 3,
                    state = pagerState
                ) { page ->

                    when (page) {
                        0 -> {
                            ScreenOne()
                        }

                        1 -> {
                            ScreenTwo()
                        }

                        2 -> {
                            ScreenThree()
                        }
                    }
                }

                HorizontalPagerIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 20.dp),
                    pagerState = pagerState,
                    activeColor = Color.Yellow,
                    inactiveColor = Color.DarkGray,
                    indicatorWidth = 8.dp,
                    indicatorHeight = 8.dp,
                    indicatorShape = CircleShape,
                    spacing = 5.dp
                )
            }
        }

    }

    @Composable
    fun ScreenOne() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color6868AD)
        ) {

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                text = "Coding",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        }
    }

    @Composable
    fun ScreenTwo() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color6868AD)
        ) {

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            Text(
                text = "With",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        }
    }

    @Composable
    fun ScreenThree() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color6868AD),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Cat",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 25.sp
            )
        }
    }

}