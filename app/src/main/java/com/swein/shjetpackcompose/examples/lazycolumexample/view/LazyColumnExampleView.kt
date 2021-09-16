package com.swein.shjetpackcompose.examples.lazycolumexample.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.lazycolumexample.model.LazyColumnExampleModel
import com.swein.shjetpackcompose.examples.lazycolumexample.viewmodel.LazyColumnExampleViewModel
import com.swein.shjetpackcompose.examples.schedulenote.commonpart.CommonView

object LazyColumnExampleView {

    private const val TAG = "LazyColumnExampleView"

    @Composable
    fun ActivityContentView(viewModel: LazyColumnExampleViewModel) {

        val coroutineScope = rememberCoroutineScope()

        Scaffold(
            backgroundColor = Color.White
        ) { // innerPadding ->
            ScaffoldContent(
                viewModel = viewModel,
                onRefresh = {
                    ILog.debug(TAG, "toggle reload")
                    viewModel.reload()
                },
                onLoadMore = {
                    ILog.debug(TAG, "toggle load more")
                    viewModel.loadMore()
                }
            )
        }
    }

    @Composable
    private fun ScaffoldContent(
        modifier: Modifier = Modifier,
        viewModel: LazyColumnExampleViewModel,
        onRefresh: () -> Unit,
        onLoadMore: (() -> Unit)? = null
    ) {

        Box(
            modifier = modifier.fillMaxSize()
        ) {

            Column(modifier = modifier.fillMaxSize()) {

                // custom tool bar
                CommonView.CustomToolBar(
                    title = "Lazy column example"
                )

                SwipeRefreshView(
                    contentView = {
                        ListView(viewModel = viewModel, onLoadMore = onLoadMore)
                    },
                    onRefresh = onRefresh
                )
            }

            Progress(viewModel = viewModel)
        }
    }

    @Composable
    private fun SwipeRefreshView(modifier: Modifier = Modifier, contentView: @Composable () -> Unit, onRefresh: () -> Unit) {
        val swipeRefreshState = rememberSwipeRefreshState(false)
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                ILog.debug(TAG, "refresh")
                onRefresh()
            },
            modifier = modifier.fillMaxSize()
        ) {
            contentView()
        }
    }

    @Composable
    private fun ListView(modifier: Modifier = Modifier, viewModel: LazyColumnExampleViewModel, onLoadMore: (() -> Unit)? = null) {

        val lazyListState = rememberLazyListState()

        LazyColumn(
            state = lazyListState,
            modifier = modifier.fillMaxSize()
        ) {

            items(
                items = viewModel.lazyColumnExampleModelList,
                key = {
                    it.index
                }
            ) { item ->

                val lastIndex = viewModel.lazyColumnExampleModelList.lastIndex
                val currentIndex = viewModel.lazyColumnExampleModelList.indexOf(item)

                ListItemView(lazyColumnExampleModel = item) {

                }

                ILog.debug(TAG, "currentIndex $currentIndex lastIndex ???? $lastIndex")
                SideEffect {
                    if (currentIndex == lastIndex) {
                        onLoadMore?.let {
                            it()
                        }
                    }
                }

            }
        }
    }


    @OptIn(ExperimentalCoilApi::class)
    @Composable
    private fun ListItemView(
        modifier: Modifier = Modifier,
        lazyColumnExampleModel: LazyColumnExampleModel,
        onItemClick: (lazyColumnExampleModel: LazyColumnExampleModel) -> Unit
    ) {

        Surface(
            modifier = modifier
                .fillMaxWidth()
                .height(130.dp)
                .padding(10.dp),
            elevation = 5.dp,
            shape = RoundedCornerShape(8.dp),
            color = Color.White
        ) {

            Row(
                modifier = modifier
                    .fillMaxSize()
                    .background(color = Color.White)
                    .clickable {
                        onItemClick(lazyColumnExampleModel)
                    }
                    .padding(8.dp),
            ) {

                ConstraintLayout(
                    listItemConstraintSet(),
                    modifier = modifier
                        .fillMaxSize()
                        .padding(start = 10.dp)
                ) {

                    Text(
                        text = "index ${lazyColumnExampleModel.index}",
                        color = colorResource(id = R.color.c111111),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = modifier.layoutId("title")
                    )

                    Text(
                        text = lazyColumnExampleModel.content,
                        color = colorResource(id = R.color.c666666),
                        fontSize = 16.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = modifier.layoutId("content"),
                    )

                }
            }
        }
    }

    private fun listItemConstraintSet(): ConstraintSet {
        return ConstraintSet {
            val title = createRefFor("title")
            val content = createRefFor("content")

            constrain(title) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }

            constrain(content) {
                top.linkTo(title.bottom, margin = 2.dp)
                start.linkTo(title.start)
            }

        }
    }

    @Composable
    private fun Progress(viewModel: LazyColumnExampleViewModel) {
        CommonView.Progress(viewModel.isIO.value)
    }

}