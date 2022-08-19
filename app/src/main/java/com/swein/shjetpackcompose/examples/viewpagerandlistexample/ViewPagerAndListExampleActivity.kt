package com.swein.shjetpackcompose.examples.viewpagerandlistexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateListOf

class ViewPagerAndListExampleActivity : ComponentActivity() {

    private val listFood = mutableStateListOf<String>()
    private val listDrink = mutableStateListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            TabRowPagerListControllerView(
                listFood,
                listDrink,
                onReload = {
                    when (it) {
                        "food" -> {
                            listFood.clear()
                            listFood.addAll(fetchDataFromDummyServer(0, 15, "food"))
                        }

                        "drink" -> {
                            listDrink.clear()
                            listDrink.addAll(fetchDataFromDummyServer(0, 15, "drink"))
                        }
                    }
                },
                onLoadMore = {
                    when (it) {
                        "food" -> {
                            listFood.addAll(fetchDataFromDummyServer(listFood.size, 10, "food"))
                        }

                        "drink" -> {
                            listDrink.addAll(fetchDataFromDummyServer(listDrink.size, 10, "drink"))
                        }
                    }
                }
            )

        }

    }

    private fun fetchDataFromDummyServer(offset: Int, limit: Int, type: String): List<String> {

        val list = mutableListOf<String>()

        for (i in offset until (offset + limit)) {

            // Let's suppose the maximum is 42, so the index is from 0 to 41
            if (i == 42) {
                return list
            }

            list.add("$type $i")
        }

        return list
    }
}