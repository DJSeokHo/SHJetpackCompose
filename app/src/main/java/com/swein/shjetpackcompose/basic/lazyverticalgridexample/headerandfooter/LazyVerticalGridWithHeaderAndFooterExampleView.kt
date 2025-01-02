package com.swein.shjetpackcompose.basic.lazyverticalgridexample.headerandfooter

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.application.ui.theme.BlackTransparent30
import com.swein.shjetpackcompose.application.ui.theme.Color6868AD
import com.swein.shjetpackcompose.basic.customthemeexample.ui.theme.Color03DAC5
import com.swein.shjetpackcompose.basic.lazyverticalgridexample.headerandfooter.model.LVGTestDataModel
import com.swein.shjetpackcompose.basic.lazyverticalgridexample.headerandfooter.model.ListItemData
import com.swein.shjetpackcompose.basic.lazyverticalgridexample.headerandfooter.viewmodel.GRID_LIST_FOOTER_COUNT
import com.swein.shjetpackcompose.basic.lazyverticalgridexample.headerandfooter.viewmodel.GRID_LIST_HEADER_COUNT
import com.swein.shjetpackcompose.basic.lazyverticalgridexample.headerandfooter.viewmodel.LazyVerticalGridWithHeaderAndFooterExampleViewModel

@Composable
fun LazyVerticalGridWithHeaderAndFooterExampleControllerView(
    viewModel: LazyVerticalGridWithHeaderAndFooterExampleViewModel
) {

    val context = LocalContext.current
    val state = viewModel.state

    ContentView(
        isLoading = state.loading.value,
        onReload = {
            ILog.debug("???", "onReload")
            viewModel.fetchData(0, 20)
        },
        onLoadMore = {
            ILog.debug("???", "onLoadMore")
            viewModel.fetchData(viewModel.list.size - GRID_LIST_HEADER_COUNT, 15)
        },
        list = viewModel.list,
        onItemClick = {
            Toast
                .makeText(
                    context,
                    "Index:${(it as LVGTestDataModel).index} item has clicked",
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
    list: MutableList<ListItemData>,
    onItemClick: (data: ListItemData) -> Unit,
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

            GridListView(
                list = list,
                onLoadMore = onLoadMore,
                onItemClick = onItemClick,
                onHeaderClick = onHeaderClick,
                onFooterClick = onFooterClick
            )
        }

        // progress
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color.Black.copy(alpha = 0.2f))
                        .padding(20.dp),
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
private fun GridListView(
    list: MutableList<ListItemData>,
    onLoadMore: () -> Unit,
    onItemClick: (data: ListItemData) -> Unit,
    onHeaderClick: () -> Unit,
    onFooterClick: () -> Unit
) {

    LazyVerticalGrid(
        columns = object : GridCells {
            override fun Density.calculateCrossAxisCellSizes(
                availableSize: Int,
                spacing: Int
            ): List<Int> {
                val firstColumn = (availableSize - spacing) * 2 / 3
                val secondColumn = (availableSize - spacing) - firstColumn
                return listOf(firstColumn, secondColumn)
            }
        },
        contentPadding = PaddingValues(
            horizontal = 2.dp,
            vertical = 4.dp
        ),
        state = rememberLazyGridState(),
        modifier = Modifier
            .fillMaxSize()
    ) {

        itemsIndexed(
            items = list,
            key = { _: Int, item: ListItemData ->
                item.hashCode()
            },
            span = { _: Int, item: ListItemData ->

                when (item.type) {
                    ListItemData.Type.HEADER -> {
                        GridItemSpan(maxLineSpan)
                    }
                    ListItemData.Type.FOOTER -> {
                        GridItemSpan(maxLineSpan)
                    }
                    else -> {
                        GridItemSpan(1)
                    }
                }
            }
        ) { index, item ->

            when (item.type) {
                ListItemData.Type.HEADER -> {
                    GridListHeaderView(onHeaderClick)
                }
                ListItemData.Type.FOOTER -> {
                    GridListFooterView(onFooterClick)
                }
                else -> {
                    GridListItemView(
                        data = item as LVGTestDataModel,
                        onItemClick = onItemClick
                    )
                }
            }

            if (index == list.size - 1 - GRID_LIST_FOOTER_COUNT) {

                LaunchedEffect(key1 = list.size, block = {
                    // use LaunchedEffect to make sure that load more only once
                    onLoadMore()
                })
            }
        }
    }

}

@Composable
private fun GridListHeaderView(
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = LocalIndication.current,
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
fun GridListFooterView(
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp, vertical = 5.dp)
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = LocalIndication.current,
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
private fun GridListItemView(
    data: LVGTestDataModel,
    onItemClick: (data: LVGTestDataModel) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 4.dp, vertical = 5.dp),
        shape = RoundedCornerShape(2.dp),
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
                        onItemClick(data)
                    }
                )
                .background(Color.White)
        ) {

            Spacer(modifier = Modifier.padding(vertical = 16.dp))

            Image(
                painter = painterResource(id = data.imageResource),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            )

            Spacer(modifier = Modifier.padding(vertical = 6.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp),
                text = "index:${data.index}\n${data.content}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.padding(vertical = 16.dp))
        }
    }
}