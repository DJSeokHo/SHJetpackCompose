package com.swein.shjetpackcompose.basic.lazyverticalgridexample.headerandfooter.dummyserver

import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.basic.lazyverticalgridexample.headerandfooter.model.LVGTestDataModel

object LazyVerticalGridWithHeaderAndFooterExampleDummyServer {

    fun fetchDataFromDummyServer(offset: Int, limit: Int): List<LVGTestDataModel> {

        val list = mutableListOf<LVGTestDataModel>()

        for (i in offset until (offset + limit)) {

            // Let's suppose the maximum is 42, so the index is from 0 to 41
            if (i == 42) {
                return list
            }

            LVGTestDataModel(
                index = i,
                imageResource = R.drawable.coding_with_cat_icon,
                content = "grid item."
            ).apply {
                list.add(this)
            }
        }

        return list
    }

}