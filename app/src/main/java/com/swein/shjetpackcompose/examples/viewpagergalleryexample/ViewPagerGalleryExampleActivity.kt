package com.swein.shjetpackcompose.examples.viewpagergalleryexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.google.accompanist.pager.rememberPagerState
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.application.ui.theme.Color6868AD

class ViewPagerGalleryExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ContentView()

        }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
private fun ContentView() {

    val pagerState = rememberPagerState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {

            HorizontalPager(
                modifier = Modifier
                    .height(340.dp),
                count = 3,
                itemSpacing = (15).dp,
                contentPadding = PaddingValues(horizontal = 40.dp),
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
}

@Composable
fun ScreenOne() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Coding",
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            fontSize = 25.sp
        )
    }
}

@Composable
fun ScreenTwo() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "With",
            fontWeight = FontWeight.Bold,
            color = Color.White,
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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.coding_with_cat_icon),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        Text(
            text = "Cat",
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = 25.sp
        )
    }
}