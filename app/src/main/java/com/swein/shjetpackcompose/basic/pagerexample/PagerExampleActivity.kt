package com.swein.shjetpackcompose.basic.pagerexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.LocalOverScrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.application.ui.theme.ColorC57644

class PagerExampleActivity : ComponentActivity() {

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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            PagerContent(pagerState)
        }

    }

    @OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
    @Composable
    private fun PagerContent(pagerState: PagerState) {

        CompositionLocalProvider(LocalOverScrollConfiguration provides null) {

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

        }

    }

    @Composable
    fun ScreenOne() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Yellow),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Coding",
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
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
                .background(Color.DarkGray),
            verticalArrangement = Arrangement.Center
        ) {
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
                .background(ColorC57644),
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.coding_with_cat_icon),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.padding(vertical = 20.dp))

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