package com.swein.shjetpackcompose.examples.schedulenote.edit.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swein.easyeventobserver.EventCenter
import com.swein.framework.utility.date.DateUtility
import com.swein.framework.utility.debug.ILog
import com.swein.framework.utility.uuid.UUIDUtil
import com.swein.shjetpackcompose.examples.schedulenote.constants.ScheduleNoteConstants
import com.swein.shjetpackcompose.examples.schedulenote.edit.service.EditScheduleService
import com.swein.shjetpackcompose.examples.schedulenote.model.ScheduleModel
import kotlinx.coroutines.*

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

    fun initWithObject(scheduleModel: ScheduleModel) {

        uuid.value = scheduleModel.uuid
        title.value = scheduleModel.title
        content.value = scheduleModel.content
        contentImage.value = scheduleModel.contentImage
        createDate.value = scheduleModel.createDate
        isImportant.value = scheduleModel.isImportant
        isUrgent.value = scheduleModel.isUrgent
        isFinished.value = scheduleModel.isFinished
    }

    fun onSave(onEmpty: () -> Unit, onFinished: () -> Unit) {

        ILog.debug(TAG, "${title.value}, ${content.value}")

        if (title.value.isEmpty()) {
            onEmpty()
            return
        }

        if (content.value.isEmpty()) {
            onEmpty()
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

                    EventCenter.sendEvent(ScheduleNoteConstants.ESS_REFRESH_SCHEDULE_LIST, this, null)

                    onFinished()
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
