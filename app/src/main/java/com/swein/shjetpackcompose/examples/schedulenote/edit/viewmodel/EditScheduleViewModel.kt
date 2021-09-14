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

    var snackBarMessage = mutableStateOf("")

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

    fun toggleSnackBar(message: String) {

        if (snackBarMessage.value != "") {
            return
        }

        snackBarMessage.value = message

        viewModelScope.launch(Dispatchers.IO) {

            delay(2000)

            viewModelScope.launch(Dispatchers.Main) {
                snackBarMessage.value = ""
            }
        }
    }

    fun onSave(checkEmpty: () -> Unit) {

        ILog.debug(TAG, "${uuid.value}, ${title.value}, ${content.value}, " +
                "${contentImage.value}, ${createDate.value}, " +
                "${isImportant.value}, ${isUrgent.value}, ${isFinished.value}")

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
                        scheduleViewModel.uuid = if (uuid.value == "") {
                            uuid.value
                        }
                        else {
                            UUIDUtil.getUUIDString()
                        }
                        scheduleViewModel.title = title.value
                        scheduleViewModel.content = content.value
                        scheduleViewModel.contentImage = contentImage.value
                        scheduleViewModel.createDate = if (createDate.value == "") {
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

                    isIO.value = false

                }
            }
            catch (e: Exception) {
                isIO.value = false
            }

        }

    }
}
