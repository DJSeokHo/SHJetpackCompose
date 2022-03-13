package com.swein.shjetpackcompose.basic.lazyverticalgridexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
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

class LazyVerticalGridExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ContentView()

        }
    }

    @Composable
    private fun ContentView() {

        LazyVerticalGridDemo()

    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun LazyVerticalGridDemo() {
        val list = createDataList()

        androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(
                horizontal = 5.dp,
                vertical = 16.dp
            ),
            content = {
                items(list.size) { index ->
                    GridItem(list[index]) {
                        Toast.makeText(
                            this@LazyVerticalGridExampleActivity,
                            it.text,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        )
    }


    @Composable
    private fun GridItem(testData: TestData, onItemClick: (testData: TestData) -> Unit) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .padding(4.dp)
                .clickable(
                    /*
                    this is custom ripple effect
                     */
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(bounded = true, color = Color.Black),
                    onClick = {
                        onItemClick(testData)
                    }
                ),
            backgroundColor = Color.DarkGray,
            elevation = 12.dp,
            shape = RoundedCornerShape(6.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .crossfade(true)
                            .data(testData.imageUrl)
                            .build(),
                        filterQuality = FilterQuality.High
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(shape = RoundedCornerShape(6.dp)),
                    contentScale = ContentScale.Crop
                )

                Text(
                    modifier = Modifier.padding(top = 6.dp, start = 6.dp, end = 6.dp),
                    text = testData.text,
                    fontSize = 10.sp,
                    color = Color.White
                )

            }

        }

    }

    private fun createDataList(): List<TestData> {

        val list = mutableListOf<TestData>()

        list.add(TestData("ELIZABETH ST", "https://64.media.tumblr.com/e7e05d3dd68faecd7ce3762cb5fa83ee/tumblr_pxz262Xbz21sfie3io1_1280.jpg"))
        list.add(TestData("THE ZIG-ZAG", "https://64.media.tumblr.com/6fd84be76444e90b1639a7865f6b0009/tumblr_pxz25pnRfG1sfie3io1_1280.jpg"))
        list.add(TestData("BRIDGE STREET AND THE EXCHANGE, SYDNEY", "https://64.media.tumblr.com/a1bed6a1843caeac48e15644845973f1/tumblr_pxz25olnlI1sfie3io1_1280.jpg"))
        list.add(TestData("FARM COVE FROM MACQUARIE STREET", "https://64.media.tumblr.com/73452e0ca302cf9179bfd500ff45f003/tumblr_pxz22yvLWm1sfie3io1_1280.jpg"))
        list.add(TestData("CELL BLOCK - OTTAWA POLICE STATION #1", "https://64.media.tumblr.com/97b595a78f0aeca82263d9f4715c5ed4/tumblr_pxz22xAvpd1sfie3io1_1280.jpg"))
        list.add(TestData("SHOWING MOAT SURROUNDING POLICE STATION, LOOKING NORTH TOWARDS C.N.R. HOTEL [OTTAWA, ONTARIO]", "https://64.media.tumblr.com/e39eb98a1719e2abd17927e6d5251025/tumblr_pxz22wvRAj1sfie3io1_1280.jpg"))
        list.add(TestData("ADMINISTRATIVE OFFICES AND CHIEF INSPECTOR’S ACCOMODATION [OTTAWA POLICE DEPARTMENT]", "https://64.media.tumblr.com/b42a00e5ef4656e7d95c40fee887217b/tumblr_pxz22uRVys1sfie3io1_1280.jpg"))
        list.add(TestData("AERIAL VIEW SHOWING AND EMPHASIZING MOAT [OTTAWA POLICE DEPARTMENT]", "https://64.media.tumblr.com/bf90ecec728f4f167a0ffcec172315b7/tumblr_pxz22sRcO71sfie3io1_1280.jpg"))
        list.add(TestData("SKYLAB LAUNCHES – MAY 14, 1973", "https://64.media.tumblr.com/ccdf1c8b523112b0b76314e5aecfa755/tumblr_ps7ycpodfV1sfie3io1_1280.jpg"))
        list.add(TestData("EXPEDITION 31 CREW PREPARES FOR LAUNCH", "https://64.media.tumblr.com/0b5469eef13ccfec6823793187117b16/tumblr_ps7ycl6ucn1sfie3io1_1280.jpg"))
        list.add(TestData("PHOTOGRAPH DEPICTING THE DECK OF HMAS WYATT EARP", "https://64.media.tumblr.com/234eebd96166341eb9b85b4ec2597e8f/tumblr_ppam3g1mq31sfie3io1_1280.jpg"))
        list.add(TestData("MOVIETONE NEWS FIELD STAFF SYDNEY, 1938", "https://64.media.tumblr.com/146ec562f2fabb11c96849f77fa0a1d2/tumblr_ppam3eF6uE1sfie3io1_1280.jpg"))
        list.add(TestData("FIRST SPACEX DRAGON APPROACHES ISS", "https://64.media.tumblr.com/026b8fe1a1b843feff56babe49d2de51/tumblr_ppam2zwV0J1sfie3io1_1280.jpg"))

        return list
    }

    private data class TestData(
        val text: String,
        val imageUrl: String
    )
}