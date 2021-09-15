package com.swein.shjetpackcompose.examples.schedulenote.edit.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swein.framework.utility.date.DateUtility
import com.swein.framework.utility.debug.ILog
import com.swein.framework.utility.uuid.UUIDUtil
import com.swein.shjetpackcompose.examples.schedulenote.edit.service.EditScheduleService
import com.swein.shjetpackcompose.examples.schedulenote.model.ScheduleModel
import kotlinx.coroutines.*
import kr.co.dotv365.android.framework.utility.parsing.ParsingUtility
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


    var isIO = mutableStateOf(false)

    fun initWithJSONObject(jsonObject: JSONObject) {

        uuid.value = ParsingUtility.parsingString(jsonObject, "uuid")
        title.value = ParsingUtility.parsingString(jsonObject, "title")
        content.value = ParsingUtility.parsingString(jsonObject, "content")
        contentImage.value = ParsingUtility.parsingString(jsonObject, "contentImage")
        createDate.value = ParsingUtility.parsingString(jsonObject, "createDate")
        isImportant.value = ParsingUtility.parsingBoolean(jsonObject, "isImportant")
        isUrgent.value = ParsingUtility.parsingBoolean(jsonObject, "isUrgent")
        isFinished.value = ParsingUtility.parsingBoolean(jsonObject, "isFinished")
    }

    fun onSave(checkEmpty: () -> Unit) {

        ILog.debug(TAG, "${title.value}, ${content.value}")

        if (title.value.isEmpty()) {
            checkEmpty()
            return
        }

        if (content.value.isEmpty()) {
            checkEmpty()
            return
        }

        viewModelScope.launch {

            isIO.value = true

            try {
                coroutineScope {

                    val insert = async {

                        val scheduleViewModel = ScheduleModel()
                        scheduleViewModel.uuid = if (uuid.value != "") {
                            uuid.value
                        }
                        else {
                            UUIDUtil.getUUIDString()
                        }
                        scheduleViewModel.title = title.value
                        scheduleViewModel.content = content.value
                        scheduleViewModel.contentImage = contentImage.value
                        scheduleViewModel.createDate = if (createDate.value != "") {
                            createDate.value
                        }
                        else {
                            DateUtility.getCurrentDateTimeString()
                        }
                        scheduleViewModel.isImportant = isImportant.value
                        scheduleViewModel.isUrgent = isUrgent.value
                        scheduleViewModel.isFinished = isFinished.value

                        ILog.debug(TAG, "$scheduleViewModel")

                        EditScheduleService.insert(scheduleViewModel)
                    }

                    val result = insert.await()
                    ILog.debug(TAG, "result $result")

                    clean()

                    isIO.value = false

                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                isIO.value = false
            }
        }
    }

    private fun clean() {
        uuid.value = ""
        title.value = ""
        content.value = ""
        contentImage.value = ""
        createDate.value = ""
        isImportant.value = false
        isUrgent.value = false
        isFinished.value = false
    }
}
