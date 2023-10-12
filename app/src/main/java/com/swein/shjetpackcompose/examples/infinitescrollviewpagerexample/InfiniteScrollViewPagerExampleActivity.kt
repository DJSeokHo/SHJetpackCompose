package com.swein.shjetpackcompose.examples.infinitescrollviewpagerexample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InfiniteScrollViewPagerExampleActivity : AppCompatActivity() {
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

    val list = listOf(
        "Banner 1",
        "Banner 2",
        "Banner 3",
        "Banner 4",
        "Banner 5"
    )

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
                    .height(150.dp),
                count = Int.MAX_VALUE,
                itemSpacing = (15).dp,
                contentPadding = PaddingValues(horizontal = 40.dp),
                state = pagerState
            ) { page ->

                list.getOrNull(page % (list.size))?.let { content ->
                    
                    BannerItemView(content = content)

                }
            }
        }
    }

    LaunchedEffect(key1 = Unit) {

        var initialPage = Int.MAX_VALUE / 2

        while (initialPage % list.size != 0) {
            initialPage++
        }
        pagerState.scrollToPage(initialPage)
    }

    // auto scroll
//    LaunchedEffect(key1 = pagerState.currentPage) {
//        launch {
//            while (true) {
//                delay(1000)
//
//                withContext(NonCancellable) {
//                    if (pagerState.currentPage + 1 in 0..Int.MAX_VALUE) {
//                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
//                    }
//                }
//            }
//        }
//    }
}

@Composable
private fun BannerItemView(
    content: String
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Cyan),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = content,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray,
            fontSize = 25.sp
        )
    }
}
