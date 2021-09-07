package com.swein.shjetpackcompose.examples.todonote.model


data class ToDoItemDataModel(
    val uuid: String = "",
    val title: String = "",
    val content: String = "",
    val contentImage: String = "",
    val createDate: String = "",
    val modifyDate: String = "",
    val isImportant: Boolean = false,
    val isUrgent: Boolean = false,
    val finished: Boolean = false
)
