package com.swein.shjetpackcompose.examples.todonote.model

enum class ToDoType {

}

data class ToDoItemDataModel(
    val uuid: String,
    val title: String,
    val content: String,
    val image: String,
    val type
)
