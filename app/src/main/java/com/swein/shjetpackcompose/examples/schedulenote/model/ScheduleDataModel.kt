package com.swein.shjetpackcompose.examples.schedulenote.model

import org.json.JSONObject

class ScheduleDataModel {
    var uuid: String = ""
    var title: String = ""
    var content: String = ""
    var contentImage: String = ""
    var createDate: String = ""
    var isImportant: Boolean = false
    var isUrgent: Boolean = false
    var isFinished: Boolean = false

    fun initWithJSONObject(jsonObject: JSONObject) {
//        uuid.value = ""
//        title.value = ""
//        content.value = ""
//        contentImage.value = ""
//        createDate.value = ""
//        isImportant.value = false
//        isUrgent.value = false
//        isFinished.value = false
    }

}
