package com.swein.shjetpackcompose.basic.lazycolumnexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.application.ui.theme.Color111111
import com.swein.shjetpackcompose.application.ui.theme.ColorC57644
import java.util.*
import kotlin.random.Random

class LazyColumnStickerHeaderExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {
            LazyColumnStickyHeader()
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    private fun LazyColumnStickyHeader() {

        val groupList = groupingTempData()

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            LazyColumn(
                modifier = Modifier
                    .background(Color.White)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                reverseLayout = false
            ) {
                groupList.forEach { group ->

                    stickyHeader(
                        key = group.uuid
                    ) {
                        StickyHeaderItem(group)
                    }

                    items(
                        items = group.list,
                        key = {
                            it.uuid
                        }
                    ) { testData ->
                        ListItem(testData)
                    }
                }
            }
        }
    }

    @Composable
    private fun StickyHeaderItem(testDataGroup: TestDataGroup) {

        Card(
            backgroundColor = ColorC57644,
            modifier = Modifier.fillMaxWidth(),
            elevation = 8.dp,
            shape = RoundedCornerShape(0.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Spacer(modifier = Modifier.padding(10.dp))

                Image(
                    painter = painterResource(id = R.drawable.coding_with_cat_icon),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.padding(10.dp))

                Text(
                    text = testDataGroup.flag,
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    fontFamily = FontFamily.Monospace,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                )

            }

        }
    }

    @Composable
    fun ListItem(testData: TestData) {

        Card(
            backgroundColor = Color.LightGray,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp, vertical = 5.dp),
            elevation = 8.dp,
            shape = RoundedCornerShape(16.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "${testData.flag} ${testData.content}",
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color111111,
                    modifier = Modifier.padding(vertical = 20.dp)
                )

            }

        }

    }

    private fun groupingTempData(): List<TestDataGroup> {

        val list = mutableListOf<TestDataGroup>()


        val tempList = createTempDate()
        if (tempList.isNotEmpty()) {

            list.add(TestDataGroup(getUUID(), tempList[0].flag))

            for (testData in tempList) {

                if (testData.flag != list.last().flag) {
                    list.add(TestDataGroup(getUUID(), testData.flag))
                }
            }
        }

        for (testDataGroup in list) {
            testDataGroup.list.clear()
            for (testData in tempList) {
                if (testData.flag == testDataGroup.flag) {
                    testDataGroup.list.add(testData)
                }
            }
        }

        return list
    }

    private fun createTempDate(): List<TestData> {

        var groupFlag = 1

        val list = mutableListOf<TestData>()

        for (i in 0 until 100) {

            list.add(
                TestData(
                    getUUID(),
                "content $i",
                "group $groupFlag"
                )
            )

            if (Random.nextInt(0, 3) == 1) {
                groupFlag++
            }
        }

        return list
    }

    private fun getUUID(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }

    data class TestData(
        val uuid: String,
        val content: String,
        val flag: String
    )

    data class TestDataGroup(
        val uuid: String,
        val flag: String
    ) {
        var list = mutableListOf<TestData>()
    }

}