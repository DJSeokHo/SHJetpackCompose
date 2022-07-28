package com.swein.shjetpackcompose.basic.lazycolumnexample.headerandfooter.dummyserver

import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.basic.lazycolumnexample.headerandfooter.model.LCTestDataModel

object LazyColumnWithHeaderAndFooterExampleDummyServer {

    fun fetchDataFromDummyServer(offset: Int, limit: Int): List<LCTestDataModel> {

        val list = mutableListOf<LCTestDataModel>()

        for (i in offset until (offset + limit)) {

            // Let's suppose the maximum is 19, so the index is from 0 to 18
            if (i == 19) {
                return list
            }

            LCTestDataModel(
                index = i,
                imageResource = R.drawable.coding_with_cat_icon,
                content = "This is a list item."
            ).apply {
                list.add(this)
            }
        }

        return list
    }

}