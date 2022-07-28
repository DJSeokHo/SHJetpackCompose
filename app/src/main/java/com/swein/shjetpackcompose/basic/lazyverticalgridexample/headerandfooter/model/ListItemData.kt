package com.swein.shjetpackcompose.basic.lazyverticalgridexample.headerandfooter.model

open class ListItemData(
    var type: Type
) {
    enum class Type {
        NORMAL, HEADER, FOOTER
    }
}
