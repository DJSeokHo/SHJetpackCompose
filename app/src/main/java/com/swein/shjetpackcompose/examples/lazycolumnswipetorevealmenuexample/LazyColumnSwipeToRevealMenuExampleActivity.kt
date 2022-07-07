package com.swein.shjetpackcompose.examples.lazycolumnswipetorevealmenuexample

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


class LazyColumnSwipeToRevealMenuExampleActivity : ComponentActivity() {

    val list = mutableStateListOf<TestItemModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            ContentView(list)

        }

        list.clear()

        val tempList = mutableListOf<TestItemModel>()
        for (i in 0 until 100) {
            tempList.add(
                TestItemModel(
                    imageUrl = "https://images.pexels.com/photos/12447940/pexels-photo-12447940.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1",
                    title = "This is title $i",
                    content = "This is content $i This is content $i This is content $i This is content $i This is content $i This is content $i This is content $i",
                    isFavorite = mutableStateOf(false)
                )
            )
        }

        list.addAll(tempList)
    }
}

@Composable
private fun ContentView(list: MutableList<TestItemModel>) {

    val content = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.White
    ) {

        LazyColumn(
            state = rememberLazyListState(),
            modifier = Modifier
                .fillMaxSize()
        ) {

            itemsIndexed(
                items = list,
                key = { _: Int, item: TestItemModel ->
                    item.hashCode()
                }
            ) { _: Int, item: TestItemModel ->

                ListItemView(content, item) { model ->
                    list.remove(model)
                }

            }
        }
    }
}

private fun dipToPx(context: Context, dipValue: Float): Float {
    return dipValue * context.resources.displayMetrics.density
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ListItemView(
    context: Context,
    model: TestItemModel,
    onDelete: (TestItemModel) -> Unit
) {

    val swipeableState = rememberSwipeableState(0)
    val scope = rememberCoroutineScope()

    // container view
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .swipeable(
                state = swipeableState,
                anchors = mapOf(
                    0f to 0,
                    -dipToPx(context, 100f) to 1,  // 100dp is a the offset dp size
                    dipToPx(context, 100f) to 2,
                ),
                thresholds = { _, _ ->
                    FractionalThreshold(0.3f)
                },
                orientation = Orientation.Horizontal
            )
            .background(Color.LightGray)
    ) {

        // show favorite when right swipe
        IconButton(
            onClick = {
                model.isFavorite.value = !model.isFavorite.value

                scope.launch {
                    swipeableState.animateTo(0, tween(600, 0))
                }
            },
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 30.dp)
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier
                    .size(40.dp)
            )
        }

        // show delete when left swipe
        IconButton(
            onClick = {
                onDelete(model)
            },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 30.dp)
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier
                    .size(40.dp)
            )
        }

        // swipe able view
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(swipeableState.offset.value.roundToInt(), 0)
                }
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.DarkGray)
        ) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.White)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight()
                ) {

                    Image(
                        painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(context = context)
                                .crossfade(true)
                                .data(model.imageUrl)
                                .size(Size.ORIGINAL)
                                .build(),
                            filterQuality = FilterQuality.Low
                        ),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentHeight()
                    )

                    if (model.isFavorite.value) {

                        Icon(
                            Icons.Filled.Favorite, null,
                            tint = Color.Yellow,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(16.dp)
                        )

                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ) {

                    Text(
                        text = model.title,
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = model.content,
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                }

                Spacer(modifier = Modifier.padding(vertical = 10.dp))
            }

        }

    }

}

data class TestItemModel(
    val imageUrl: String,
    val title: String,
    val content: String,
    val isFavorite: MutableState<Boolean>
)
