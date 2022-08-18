package com.swein.shjetpackcompose.examples.pdfreaderexample

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.swein.shjetpackcompose.examples.pdfreaderexample.viewmodel.PDFReaderViewModel
import com.swein.shjetpackcompose.examples.pdfreaderexample.viewmodel.PDFReaderViewModelState

@Composable
fun PDFReaderExampleControllerView(
    viewModel: PDFReaderViewModel
) {

    val context = LocalContext.current
    val state = viewModel.state

    PDFReaderExampleView(
        context,
        state,
        viewModel.bitmapList,
        onLoadMore = {
            viewModel.fetchData(viewModel.bitmapList.size, 10)
        }
    )

    LaunchedEffect(key1 = null, block = {

        // change to your pdf file link
        viewModel.pdf(context, "https://file-examples.com/storage/fe5947fd2362fc197a3c2df/2017/10/file-example_PDF_1MB.pdf") {

            viewModel.state.pdfFile.value?.let {

                viewModel.bitmaps(
                    it, getScreenWidth(context)
                ) {

                    viewModel.fetchData(0, 10)

                }

            }

        }

    })
}

@Composable
private fun PDFReaderExampleView(
    context: Context,
    state: PDFReaderViewModelState,
    list: MutableList<Bitmap>,
    onLoadMore: () -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            LazyColumn(
                contentPadding = PaddingValues(
                    horizontal = 2.dp
                ),
                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxSize()
            ) {

                itemsIndexed(
                    items = list,
                    key = { _: Int, item: Bitmap ->
                        item.hashCode()
                    }
                ) { index, item ->

                    ListItemView(index, item)

                    if (index == list.size - 1) {

                        LaunchedEffect(key1 = list.size, block = {
                            // use LaunchedEffect to make sure that load more only once
                            onLoadMore()
                        })
                    }
                }
            }


            // progress
            if (state.loading.value) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { },
                    color = Color.Black.copy(alpha = 0.7f)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.White
                        )

                        Text(
                            text = state.progress.value,
                            color = Color.White,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            if (state.error.value != "") {
                Toast.makeText(context, state.error.value, Toast.LENGTH_SHORT).show()
            }

        }
    }
}


@Composable
private fun ListItemView(
    index: Int,
    bitmap: Bitmap
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .crossfade(true)
                    .data(bitmap)
                    .size(Size.ORIGINAL)
                    .build(),
                filterQuality = FilterQuality.High
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(shape = RoundedCornerShape(6.dp)),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = "page ${index + 1}",
            fontSize = 8.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(6.dp)
                .background(Color.Black.copy(0.5f))
                .clip(RoundedCornerShape(6.dp))
                .padding(4.dp)
        )
    }

    Divider(color = Color.Black, thickness = 0.8.dp)
}

private fun getScreenWidth(context: Context): Int {

    val outMetrics = DisplayMetrics()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

        val display = context.display
        display?.getRealMetrics(outMetrics)
    }
    else {

        val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        display.getMetrics(outMetrics)

    }

    return outMetrics.widthPixels
}