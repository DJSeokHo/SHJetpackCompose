package com.swein.shjetpackcompose.basic.lazycolumnexample.headerandfooter

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.application.ui.theme.BlackTransparent30
import com.swein.shjetpackcompose.application.ui.theme.Color6868AD
import com.swein.shjetpackcompose.basic.lazycolumnexample.headerandfooter.model.LCTestDataModel
import com.swein.shjetpackcompose.basic.lazycolumnexample.headerandfooter.viewmodel.LazyColumnWithHeaderAndFooterExampleViewModel

@Composable
fun LazyColumnWithHeaderAndFooterExampleControllerView(
    viewModel: LazyColumnWithHeaderAndFooterExampleViewModel
) {

    val context = LocalContext.current
    val state = viewModel.state

    ContentView(
        isLoading = state.loading.value,
        onReload = {
            ILog.debug("???", "onReload")
            viewModel.fetchData(0, 10)
        },
        onLoadMore = {
            ILog.debug("???", "onLoadMore")
            viewModel.fetchData(viewModel.list.size, 5)
        },
        list = viewModel.list,
        onItemClick = {
            Toast
                .makeText(
                    context,
                    "Index:${it.index} item has clicked",
                    Toast.LENGTH_SHORT
                )
                .show()
        },
        onHeaderClick = {
            Toast
                .makeText(
                    context,
                    "header has clicked",
                    Toast.LENGTH_SHORT
                )
                .show()
        },
        onFooterClick = {
            Toast
                .makeText(
                    context,
                    "footer has clicked",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
    )

    LaunchedEffect(key1 = null, block = {
        viewModel.fetchData(0, 10)
    })
}

@Composable
private fun ContentView(
    isLoading: Boolean,
    onReload: () -> Unit,
    onLoadMore: () -> Unit,
    list: MutableList<LCTestDataModel>,
    onItemClick: (data: LCTestDataModel) -> Unit,
    onHeaderClick: () -> Unit,
    onFooterClick: () -> Unit
) {

    val swipeRefreshState = rememberSwipeRefreshState(false)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = onReload,
            modifier = Modifier.fillMaxSize()
        ) {

            ListView(
                list = list,
                onLoadMore = onLoadMore,
                onItemClick = onItemClick,
                onHeaderClick = onHeaderClick,
                onFooterClick = onFooterClick
            )
        }

        // progress
        if (isLoading) {
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
}

@Composable
private fun ListView(
    list: MutableList<LCTestDataModel>,
    onLoadMore: () -> Unit,
    onItemClick: (data: LCTestDataModel) -> Unit,
    onHeaderClick: () -> Unit,
    onFooterClick: () -> Unit
) {

    LazyColumn(
        state = rememberLazyListState(),
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        itemsIndexed(
            items = list,
            key = { _: Int, item: LCTestDataModel ->
                item.hashCode()
            }
        ) { index, item ->

            ListItemView(
                data = item,
                onItemClick = onItemClick,
                onHeaderClick = if (index == 0) {
                    // add header
                    onHeaderClick
                }
                else {
                    null
                },
                onFooterClick = if (index == list.size - 1) {
                    onFooterClick
                }
                else {
                    null
                }
            )

            if (index == list.size - 1) {

                LaunchedEffect(key1 = list.size, block = {
                    // use LaunchedEffect to make sure that load more only once
                    onLoadMore()
                })
            }
        }
    }

}

@Composable
private fun ListHeaderView(
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(
                    bounded = true,
                    color = Color.Black
                ),
                onClick = onClick
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = 10.dp,
        backgroundColor = Color.Yellow
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "I am a header view",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )
        }
    }
}

@Composable
private fun ListFooterView(
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(
                    bounded = true,
                    color = Color.Black
                ),
                onClick = onClick
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = 10.dp,
        backgroundColor = Color.Blue
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentAlignment = Alignment.Center
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "I am a footer view",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
private fun ListItemView(
    data: LCTestDataModel,
    onItemClick: (data: LCTestDataModel) -> Unit,
    onHeaderClick: (() -> Unit)? = null,
    onFooterClick: (() -> Unit)? = null,
) {

    Column {

        onHeaderClick?.let {
            ListHeaderView(it)
        }

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
                        interactionSource = MutableInteractionSource(),
                        indication = rememberRipple(
                            bounded = true,
                            color = Color.Black
                        ),
                        onClick = {
                            onItemClick(data)
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
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.padding(vertical = 16.dp))
            }
        }

        onFooterClick?.let {
            ListFooterView(it)
        }
    }
}