package com.swein.shjetpackcompose.examples.schedulenote.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SCHEDULE_TABLE")
data class ScheduleEntity(

    @PrimaryKey
    @ColumnInfo(name = "UUID")
    var uuid: String,

    @ColumnInfo(name = "TITLE")
    var title: String,

    @ColumnInfo(name = "CONTENT")
    var content: String,

    @ColumnInfo(name = "CONTENT_IMAGE")
    var contentImage: String,

    @ColumnInfo(name = "CREATE_DATE")
    var createDate: String,

    @ColumnInfo(name = "IS_IMPORTANT")
    var isImportant: Boolean,

    @ColumnInfo(name = "IS_URGENT")
    var isUrgent: Boolean,

    @ColumnInfo(name = "IS_FINISHED")
    var isFinished: Boolean,

    @ColumnInfo(name = "TAG")
    var tag: String
)