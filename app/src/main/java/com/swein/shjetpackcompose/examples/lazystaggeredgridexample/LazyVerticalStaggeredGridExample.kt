package com.swein.shjetpackcompose.examples.lazystaggeredgridexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class LazyVerticalStaggeredGridExample : ComponentActivity() {

    private var list = mutableStateListOf<ItemData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            ContentView(list)

        }
//        list.add(ItemData(0, "ELIZABETH ST", "https://64.media.tumblr.com/e7e05d3dd68faecd7ce3762cb5fa83ee/tumblr_pxz262Xbz21sfie3io1_1280.jpg"))
//        list.add(ItemData(1, "THE ZIG-ZAG", "https://64.media.tumblr.com/6fd84be76444e90b1639a7865f6b0009/tumblr_pxz25pnRfG1sfie3io1_1280.jpg"))
//        list.add(ItemData(2, "BRIDGE STREET AND THE EXCHANGE, SYDNEY", "https://64.media.tumblr.com/a1bed6a1843caeac48e15644845973f1/tumblr_pxz25olnlI1sfie3io1_1280.jpg"))
//        list.add(ItemData(3, "FARM COVE FROM MACQUARIE STREET", "https://64.media.tumblr.com/73452e0ca302cf9179bfd500ff45f003/tumblr_pxz22yvLWm1sfie3io1_1280.jpg"))
//        list.add(ItemData(4, "CELL BLOCK - OTTAWA POLICE STATION #1", "https://64.media.tumblr.com/97b595a78f0aeca82263d9f4715c5ed4/tumblr_pxz22xAvpd1sfie3io1_1280.jpg"))
//        list.add(ItemData(5, "SHOWING MOAT SURROUNDING POLICE STATION, LOOKING NORTH TOWARDS C.N.R. HOTEL [OTTAWA, ONTARIO]", "https://64.media.tumblr.com/e39eb98a1719e2abd17927e6d5251025/tumblr_pxz22wvRAj1sfie3io1_1280.jpg"))
//        list.add(ItemData(6, "ADMINISTRATIVE OFFICES AND CHIEF INSPECTOR’S ACCOMODATION [OTTAWA POLICE DEPARTMENT]", "https://64.media.tumblr.com/b42a00e5ef4656e7d95c40fee887217b/tumblr_pxz22uRVys1sfie3io1_1280.jpg"))
//        list.add(ItemData(7, "AERIAL VIEW SHOWING AND EMPHASIZING MOAT [OTTAWA POLICE DEPARTMENT]", "https://64.media.tumblr.com/bf90ecec728f4f167a0ffcec172315b7/tumblr_pxz22sRcO71sfie3io1_1280.jpg"))
//        list.add(ItemData(8, "SKYLAB LAUNCHES – MAY 14, 1973", "https://64.media.tumblr.com/ccdf1c8b523112b0b76314e5aecfa755/tumblr_ps7ycpodfV1sfie3io1_1280.jpg"))
//        list.add(ItemData(9, "EXPEDITION 31 CREW PREPARES FOR LAUNCH", "https://64.media.tumblr.com/0b5469eef13ccfec6823793187117b16/tumblr_ps7ycl6ucn1sfie3io1_1280.jpg"))
//        list.add(ItemData(10, "PHOTOGRAPH DEPICTING THE DECK OF HMAS WYATT EARP", "https://64.media.tumblr.com/234eebd96166341eb9b85b4ec2597e8f/tumblr_ppam3g1mq31sfie3io1_1280.jpg"))
//        list.add(ItemData(11, "MOVIETONE NEWS FIELD STAFF SYDNEY, 1938", "https://64.media.tumblr.com/146ec562f2fabb11c96849f77fa0a1d2/tumblr_ppam3eF6uE1sfie3io1_1280.jpg"))
//        list.add(ItemData(12, "FIRST SPACEX DRAGON APPROACHES ISS", "https://64.media.tumblr.com/026b8fe1a1b843feff56babe49d2de51/tumblr_ppam2zwV0J1sfie3io1_1280.jpg"))

        lifecycleScope.launch(Dispatchers.Main.immediate) {

            list.clear()
            for (i in 0 until 1000) {
                list.add(
                    ItemData(
                        i,
                        Random.nextInt(100, 300),
                        "index $i",
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ContentView(list: List<ItemData>) {

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
    ) {

//        LazyVerticalStaggeredGrid(
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            LazyHorizontalStaggeredGrid(
                state = rememberLazyStaggeredGridState(),
//            columns = StaggeredGridCells.Fixed(2),
                rows = StaggeredGridCells.Fixed(3),
                modifier = Modifier
                    .height(500.dp),
                contentPadding = PaddingValues(10.dp), // out padding
                horizontalItemSpacing = 5.dp, // inner padding
                verticalArrangement = Arrangement.spacedBy(5.dp) // inner padding
            ) {

                itemsIndexed(
                    items = list,
                    key = { _: Int, item: ItemData ->
//                    item.index // if you use class, this is better
                        item.hashCode() // if you use data class, this is better
                    }
                ) {  _, item ->

                    ItemView(item)
                }
            }

        }
    }
}

@Composable
private fun ItemView(itemData: ItemData) {

    Box(
        modifier = Modifier
//            .fillMaxWidth()
//            .height(itemData.height.dp)
            .fillMaxHeight()
            .width(itemData.width.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {

        Text(
            text = itemData.content,
            color = Color.White,
            fontSize = 23.sp
        )
    }
}

private data class ItemData(
    val index: Int,
//    val height: Int,
    val width: Int,
    val content: String
)