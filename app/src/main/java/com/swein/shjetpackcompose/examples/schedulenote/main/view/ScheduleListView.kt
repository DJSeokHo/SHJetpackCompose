package com.swein.shjetpackcompose.examples.schedulenote.main.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.request.CachePolicy
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.schedulenote.commonpart.CommonView
import com.swein.shjetpackcompose.examples.schedulenote.main.viewmodel.ScheduleListViewModel
import com.swein.shjetpackcompose.examples.schedulenote.model.ScheduleModel
import java.io.File

object ScheduleListView {

    private const val TAG = "ScheduleListView"

    @Composable
    fun ActivityContentView(modifier: Modifier = Modifier, viewModel: ScheduleListViewModel, onToolBarEndClick: () -> Unit) {

//        val coroutineScope = rememberCoroutineScope()

        Scaffold(
            backgroundColor = Color.White
        ) { // innerPadding ->

            Box(
                modifier = modifier.fillMaxSize()
            ) {

                Column(modifier = modifier.fillMaxSize()) {

                    // custom tool bar
                    CommonView.CustomToolBar(
                        endImageResource = R.mipmap.ti_plus,
                        onEndClick = {
                            onToolBarEndClick()
                        }
                    )

                    SwipeRefreshView(
                        contentView = {
                            ListView(list = viewModel.list, onLoadMore = {
                                viewModel.loadMore()
                            })
                        },
                        onRefresh = {
                            viewModel.reload()
                        }
                    )
                }

                if (viewModel.isIO.value) {
                    CommonView.Progress()
                }
            }

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
    private fun ListView(modifier: Modifier = Modifier, list: MutableList<ScheduleModel>, onLoadMore: (() -> Unit)? = null) {

        val lazyListState = rememberLazyListState()

        LazyColumn(
            state = lazyListState,
            modifier = modifier.fillMaxSize()
        ) {

            itemsIndexed(
                items = list
            ) { index, item ->

                ListItemView(scheduleModel = item) {

                }

                ILog.debug(TAG, "index $index")
                if (index == list.size - 1) {
                    onLoadMore?.let {
                        it()
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalCoilApi::class)
    @Composable
    private fun ListItemView(
        modifier: Modifier = Modifier,
        scheduleModel: ScheduleModel,
        onItemClick: (scheduleModelModel: ScheduleModel) -> Unit
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
                        onItemClick(scheduleModel)
                    }
                    .padding(8.dp),
            ) {

                Surface(
                    modifier = modifier.size(60.dp),
//                    shape = CircleShape,
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
                ) {
                    // Image area
                    Image(
                        painter = rememberImagePainter(
                            data = File(scheduleModel.contentImage),
                            builder = {
                                crossfade(true)
                                placeholder(R.drawable.coding_with_cat_icon)
                                memoryCachePolicy(CachePolicy.DISABLED)
                            }
                        ),
                        contentDescription = "Android Logo",
                        modifier = Modifier.size(60.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                ConstraintLayout(
                    listItemConstraintSet(),
                    modifier = modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .padding(start = 10.dp)
                ) {

                    Text(
                        text = scheduleModel.title,
                        color = colorResource(id = R.color.c111111),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = modifier.layoutId("title")
                    )

                    Text(
                        text = scheduleModel.content,
                        color = colorResource(id = R.color.c666666),
                        fontSize = 16.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = modifier.layoutId("content"),
                    )

                    Text(
                        text = scheduleModel.createDate,
                        color = colorResource(id = R.color.c999999),
                        fontSize = 10.sp,
                        maxLines = 1,
                        modifier = modifier.layoutId("date")
                    )

                    Row(
                        modifier = modifier.layoutId("state")
                    ) {

                        if (scheduleModel.isFinished) {
                            Image(
                                painter = painterResource(id = R.mipmap.ti_finished),
                                contentDescription = "",
                                contentScale = ContentScale.Fit,
                                modifier = modifier
                                    .padding(end = 4.dp)
                                    .size(16.dp)
                            )
                        }

                        if (scheduleModel.isImportant) {
                            Image(
                                painter = painterResource(id = R.mipmap.ti_important),
                                contentDescription = "",
                                contentScale = ContentScale.Fit,
                                modifier = modifier
                                    .padding(end = 4.dp)
                                    .size(16.dp)
                            )
                        }

                        if (scheduleModel.isUrgent) {
                            Image(
                                painter = painterResource(id = R.mipmap.ti_urgent),
                                contentDescription = "",
                                contentScale = ContentScale.Fit,
                                modifier = modifier
                                    .padding(end = 4.dp)
                                    .size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    private fun listItemConstraintSet(): ConstraintSet {
        return ConstraintSet {
            val title = createRefFor("title")
            val content = createRefFor("content")
            val date = createRefFor("date")

            val state = createRefFor("state")

            constrain(title) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }

            constrain(content) {
                top.linkTo(title.bottom, margin = 2.dp)
                start.linkTo(title.start)
            }

            constrain(date) {
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }

            constrain(state) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }

        }
    }

}

@Preview(showBackground = true, name = "schedule list view")
@Composable
fun ScheduleListViewPreview() {

}