package com.swein.shjetpackcompose.examples.skeletonexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.swein.shjetpackcompose.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SkeletonListExampleActivity : ComponentActivity() {

    private val list = mutableStateListOf<TestDataModel>()

    private var skeletonFlag = mutableStateOf(false)
    private var loadMoreFlag = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            ContentView(
                list = list,
                onRefresh = {
                    reload()
                },
                onLoadMore = {
                    loadMore()
                },
                skeletonFlag = skeletonFlag.value,
                loadMoreFlag = loadMoreFlag.value
            )
        }

        reload()
    }

    /**
     * skeleton UI only for reloading data
     */
    private fun reload() {

        lifecycleScope.launch {

            toggleSkeleton(true)

            val task = async(Dispatchers.IO) {
                delay(2000)
                dummyData(0, 20)
            }

            val result = task.await()

            list.clear()
            list.addAll(result)

            toggleSkeleton(false)
        }
    }

    /**
     * scroll to bottom to load more data
     */
    private fun loadMore() {

        lifecycleScope.launch {

            loadMoreFlag.value = true

            val task = async(Dispatchers.IO) {
                delay(1000)
                dummyData(list.size, 10)
            }

            val result = task.await()

            loadMoreFlag.value = false

            list.addAll(result)
        }
    }

    private fun dummyData(offset: Int, limit: Int): List<TestDataModel> {

        val list = mutableListOf<TestDataModel>()
        var model: TestDataModel

        for (i in offset until offset + limit) {
            model = TestDataModel(
                index = i,
                resource = R.drawable.coding_with_cat_icon,
                title = "title $i",
                content = "content $i content $i content $i content $i content $i"
            )
            list.add(model)
        }

        return list
    }

    private fun toggleSkeleton(flag: Boolean) {

        skeletonFlag.value = flag

        if (flag) {
            list.clear()
            list.addAll(skeletonData())
        }
    }

    // create 20 empty data for skeleton
    private fun skeletonData(): List<TestDataModel> {

        val list = mutableListOf<TestDataModel>()
        var model: TestDataModel

        for (i in 0 until 20) {
            model = TestDataModel(
                index = i
            )
            list.add(model)
        }


        return list
    }
}

@Composable
private fun ContentView(
    modifier: Modifier = Modifier, list: MutableList<TestDataModel>,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    skeletonFlag: Boolean,
    loadMoreFlag: Boolean
) {


    Surface(
        color = Color.White,
        modifier = modifier.fillMaxSize()
    ) {

        SwipeRefresh(
            state = rememberSwipeRefreshState(false),
            onRefresh = {
                onRefresh()
            },
            modifier = modifier.fillMaxSize()
        ) {

            Box(
                modifier = modifier.fillMaxSize()
            ) {

                LazyColumn(
                    state = rememberLazyListState(),
                    modifier = modifier
                        .fillMaxSize()
                ) {

                    items(
                        items = list,
                        key = {
                            it.index
                        }
                    ) { item ->

                        val lastIndex = list.lastIndex
                        val currentIndex = list.indexOf(item)

                        ListItemView(model = item, skeletonFlag = skeletonFlag)

                        if (currentIndex == lastIndex) {
                            onLoadMore()
                        }

                    }
                }

                // handle the touch event when loading or reloading
                if (skeletonFlag || loadMoreFlag) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable(
                                interactionSource = remember {
                                    MutableInteractionSource()
                                },
                                indication = null,
                                onClick = { }
                            ),
                        contentAlignment = Alignment.Center
                    ) {

                        // if it's loading more data, not reloading,
                        // then show the circle progress instead of skeleton
                        if (loadMoreFlag) {
                            CircularProgressIndicator(
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun ListItemView(
    modifier: Modifier = Modifier,
    model: TestDataModel,
    skeletonFlag: Boolean
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        SkeletonWrapperView(
            contentView = {

                if (model.resource != 0) {
                    Image(
                        painter = painterResource(
                            id = model.resource
                        ),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )
                }
            },
            defaultPrototypeView = {

                Box(
                    modifier =
                    modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
            },
            shape = CircleShape,
            loading = skeletonFlag
        )

        Spacer(modifier = modifier.padding(horizontal = 10.dp))

        Column(
            modifier = modifier
                .wrapContentHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {

            SkeletonWrapperView(
                contentView = {
                    Text(
                        text = model.title,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                },
                defaultPrototypeView = {
                    Text(
                        text = "xxxxxxxxxxxx",
                        fontSize = 16.sp,
                    )
                },
                loading = skeletonFlag
            )

            Spacer(modifier = modifier.padding(vertical = 6.dp))

            SkeletonWrapperView(
                contentView = {
                    Text(
                        text = model.content,
                        color = Color.Gray,
                        fontSize = 16.sp,
                    )
                },
                defaultPrototypeView = {
                    Text(
                        text = "xxxxxxxxxxxxxxxxxxxx\nxxxxxxxxxxxxxxxxxxxx",
                        fontSize = 12.sp,
                    )
                },
                loading = skeletonFlag
            )


        }
    }

}

private data class TestDataModel(
    val index: Int = 0,
    val resource: Int = 0,
    val title: String = "",
    val content: String = ""
)

@Composable
private fun SkeletonWrapperView(
    modifier: Modifier = Modifier,
    contentView: @Composable () -> Unit,
    defaultPrototypeView: (@Composable () -> Unit)? = null,
    shape: Shape = RectangleShape,
    loading: Boolean
) {

    val defaultSize = remember {
        mutableStateOf(IntSize.Zero)
    }

    Box(
        modifier = modifier
            .wrapContentSize()
            .clip(shape)
            .onSizeChanged { intSize ->
                defaultSize.value = intSize
            },
        contentAlignment = Alignment.Center
    ) {

        // the real view after loading data
        contentView()

        if (loading) {
            defaultPrototypeView?.let {
                // if has default prototype view, show it
                it()
            }
        }

        if (loading) {

            val animateColor = remember {
                Animatable(Color.LightGray) // default
            }

            val animationToggle = remember {
                mutableStateOf(false)
            }

            // if it is loading, show skeleton with animation

            // this is my animation, something like breathing light
            // you can DIY the animation
            if (animationToggle.value) {
                LaunchedEffect(key1 = null, block = {

                    animateColor.animateTo(
                        Color.Gray,
                        animationSpec = tween(400)
                    )

                    animationToggle.value = false
                })
            }
            else {
                LaunchedEffect(key1 = null, block = {

                    animateColor.animateTo(
                        Color.LightGray,
                        animationSpec = tween(400)
                    )

                    animationToggle.value = true
                })
            }

            Box(
                modifier = modifier
                    .background(animateColor.value)
                    .then(
                        with(LocalDensity.current) {
                            Modifier.size(
                                width = defaultSize.value.width.toDp(),
                                height = defaultSize.value.height.toDp(),
                            )
                        }
                    )
            )
        }

    }
}