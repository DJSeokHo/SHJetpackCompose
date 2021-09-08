package com.swein.shjetpackcompose.examples.schedulenote.edit.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.examples.schedulenote.model.ScheduleDataModel
import org.json.JSONObject

class EditScheduleViewModel: ViewModel() {

    companion object {
        private const val TAG = "EditScheduleViewModel"
    }

    var uuid = mutableStateOf("")
        private set

    var title = mutableStateOf("")

    var content = mutableStateOf("")

    var contentImage = mutableStateOf("")

    var createDate = mutableStateOf("")
        private set

    var isImportant = mutableStateOf(false)

    var isUrgent = mutableStateOf(false)

    var isFinished = mutableStateOf(false)

    fun initWithJSONObject(jsonObject: JSONObject? = null) {

        jsonObject?.let {

            uuid.value = scheduleViewModel!!.uuid
            title.value = scheduleViewModel!!.title
            content.value = scheduleViewModel!!.content
            contentImage.value = scheduleViewModel!!,
            createDate.value = scheduleViewModel!!
            isImportant.value = scheduleViewModel!!
            isUrgent.value = scheduleViewModel!!
            isFinished.value = scheduleViewModel!!

        }

    }

    fun onSave() {

    }
}
