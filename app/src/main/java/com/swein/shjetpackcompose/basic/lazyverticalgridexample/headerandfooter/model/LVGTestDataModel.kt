package com.swein.shjetpackcompose.basic.lazyverticalgridexample.headerandfooter.model

data class LVGTestDataModel(
    val index: Int,
    val imageResource: Int,
    val content: String,
) : ListItemData(Type.NORMAL)
