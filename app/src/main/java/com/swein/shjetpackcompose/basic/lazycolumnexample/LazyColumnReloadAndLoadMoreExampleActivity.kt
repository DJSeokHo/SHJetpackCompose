package com.swein.shjetpackcompose.basic.lazycolumnexample

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.application.ui.theme.BlackTransparent30
import com.swein.shjetpackcompose.application.ui.theme.Color6868AD
import kotlinx.coroutines.launch

class LazyColumnReloadAndLoadMoreExampleActivity : ComponentActivity() {

    private var list = mutableStateListOf<TestData>()
    private var isLoading = mutableStateOf(false)

    private var handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {
            ContentView()
        }

        lifecycleScope.launch {

            whenCreated {

                reload(
                    onLoading = {
                        isLoading.value = true
                    },
                    onLoaded = {
                        isLoading.value = false
                    }
                )
            }
        }

    }


    @Composable
    private fun ContentView() {

        val swipeRefreshState = rememberSwipeRefreshState(false)

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {

                    reload(onLoading = {
                        swipeRefreshState.isRefreshing = true
                        isLoading.value = true
                    },
                        onLoaded = {
                            swipeRefreshState.isRefreshing = false
                            isLoading.value = false
                        })
                },
                modifier = Modifier.fillMaxSize()
            ) {

                LazyColumn(
                    state = rememberLazyListState(),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {

                    items(
                        items = list
                    ) { item ->

                        val lastIndex = list.lastIndex
                        val currentIndex = list.indexOf(item)

                        if (item.index % 3 == 0) {
                            BigItem(data = item)
                        }
                        else {
                            SmallItem(data = item)
                        }

                        if (currentIndex == lastIndex) {

                            LaunchedEffect(key1 = list.size, block = {
                                loadMore(
                                    offset = list.size,
                                    onLoading = {
                                        isLoading.value = true
                                    },
                                    onLoaded = {
                                        isLoading.value = false
                                    }
                                )
                            })
                        }
                    }
                }
            }

            Progress(isLoading.value)

        }

    }

    @Composable
    private fun SmallItem(data: TestData) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 5.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = 10.dp
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = LocalIndication.current,
                        onClick = {
                            Toast
                                .makeText(
                                    this@LazyColumnReloadAndLoadMoreExampleActivity,
                                    "Index:${data.index} is a small item",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    )
                    .background(Color.White)
            ) {

                Image(
                    painter = painterResource(id = data.imageResource),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp))
                )

                Spacer(modifier = Modifier.padding(horizontal = 6.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterVertically),
                    text = "index:${data.index} - ${data.content}",
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )

            }

        }


    }

    @Composable
    private fun BigItem(data: TestData) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 5.dp),
            shape = RoundedCornerShape(8.dp),
            elevation = 10.dp
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = LocalIndication.current,
                        onClick = {
                            Toast
                                .makeText(
                                    this@LazyColumnReloadAndLoadMoreExampleActivity,
                                    "Index:${data.index} is a big item",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    )
                    .background(Color.White)
            ) {

                Image(
                    painter = painterResource(id = data.imageResource),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                )

                Spacer(modifier = Modifier.padding(vertical = 6.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    text = "index:${data.index} - ${data.content}",
                    fontSize = 16.sp,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.padding(vertical = 16.dp))
            }

        }

    }

    @Composable
    fun Progress(active: Boolean = true) {

        if (active) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { },
                color = BlackTransparent30
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color6868AD
                    )
                }
            }
        }
    }

    private fun reload(onLoading: () -> Unit, onLoaded: () -> Unit) {

        onLoading()

        Thread {

            val tempList = createTempData(0, 20)

            Thread.sleep(1500)

            handler.post {

                list.clear()
                list.addAll(tempList)

                onLoaded()
            }

        }.start()

    }

    private fun loadMore(offset: Int, onLoading: () -> Unit, onLoaded: () -> Unit) {

        ILog.debug("???", "loadMore $offset")

        onLoading()

        Thread {

            val tempList = createTempData(offset, 10)

            Thread.sleep(1000)

            handler.post {

                list.addAll(tempList)

                onLoaded()
            }

        }.start()
    }


    private fun createTempData(offset: Int, limit: Int): List<TestData> {
        
        val list = mutableListOf<TestData>()
        
        for (i in offset until (offset + limit)) {
            TestData(
                index = i,
                imageResource = if (i % 3 == 0) {
                    R.drawable.test_image_1
                }
                else {
                    R.drawable.coding_with_cat_icon
                },
                content = if (i % 3 == 0) {
                    "This is a big item. The big item has a big image and a content in it."
                }
                else {
                    "This is a small Item. Subscribe me is useful"
                }
            ).apply { 
                list.add(this)
            }
        }
        
        return list
    }

    data class TestData (
        val index: Int,
        val imageResource: Int,
        val content: String
    )
}

