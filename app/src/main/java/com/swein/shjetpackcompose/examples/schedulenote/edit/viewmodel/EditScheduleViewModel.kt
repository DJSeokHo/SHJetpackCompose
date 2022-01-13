package com.swein.shjetpackcompose.examples.schedulenote.edit.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swein.androidkotlintool.framework.utility.eventsplitshot.eventcenter.EventCenter
import com.swein.framework.utility.date.DateUtility
import com.swein.framework.utility.debug.ILog
import com.swein.framework.utility.uuid.UUIDUtility
import com.swein.shjetpackcompose.examples.schedulenote.constants.ScheduleNoteConstants
import com.swein.shjetpackcompose.examples.schedulenote.edit.service.EditScheduleService
import com.swein.shjetpackcompose.examples.schedulenote.model.ScheduleModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

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

    fun loadSchedule(uuid: String) {
        ILog.debug(TAG, "loadSchedule $uuid")

        viewModelScope.launch {

            isIO.value = true

            try {
                coroutineScope {

                    val schedule = async {
                        EditScheduleService.load(uuid)
                    }

                    val scheduleResult = schedule.await()

                    isIO.value = false

                    scheduleResult?.let {
                        ILog.debug(TAG, "$scheduleResult")

                        initValue(it)

                    }

                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                isIO.value = false
            }
        }
    }

    private fun initValue(scheduleModel: ScheduleModel) {
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

                        val scheduleModel = ScheduleModel()
                        scheduleModel.uuid = UUIDUtility.getUUIDString()
                        scheduleModel.title = title.value
                        scheduleModel.content = content.value
                        scheduleModel.contentImage = contentImage.value
                        scheduleModel.createDate = DateUtility.getCurrentDateTimeString()
                        scheduleModel.isImportant = isImportant.value
                        scheduleModel.isUrgent = isUrgent.value
                        scheduleModel.isFinished = false
                        scheduleModel.tag = "NONE"
                        ILog.debug(TAG, "$scheduleModel")

                        EditScheduleService.insert(scheduleModel)
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


    fun onUpdate(onEmpty: () -> Unit, onFinished: () -> Unit) {

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

                    val scheduleModel = ScheduleModel()

                    val update = async {

                        scheduleModel.uuid = uuid.value
                        scheduleModel.title = title.value
                        scheduleModel.content = content.value
                        scheduleModel.contentImage = contentImage.value
                        scheduleModel.createDate = createDate.value
                        scheduleModel.isImportant = isImportant.value
                        scheduleModel.isUrgent = isUrgent.value
                        scheduleModel.isFinished = isFinished.value

                        ILog.debug(TAG, "$scheduleModel")

                        EditScheduleService.update(scheduleModel)
                    }

                    val result = update.await()
                    ILog.debug(TAG, "result $result")

                    if (result == 1) {
                        clean()

                        isIO.value = false

                        val data = mutableMapOf<String, Any>()
                        data["scheduleModel"] = scheduleModel
                        EventCenter.sendEvent(ScheduleNoteConstants.ESS_UPDATE_SCHEDULE_ITEM, this, data)

                        onFinished()
                    }
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                isIO.value = false
            }
        }
    }

    fun onDelete(onFinished: () -> Unit) {

        ILog.debug(TAG, "delete ${uuid.value}")

        viewModelScope.launch {

            isIO.value = true

            try {
                coroutineScope {

                    val update = async {
                        EditScheduleService.delete(uuid.value)
                    }

                    val result = update.await()
                    ILog.debug(TAG, "result $result")

                    if (result == 1) {

                        isIO.value = false

                        val data = mutableMapOf<String, Any>()
                        data["uuid"] = uuid.value
                        EventCenter.sendEvent(ScheduleNoteConstants.ESS_DELETE_SCHEDULE_ITEM, this, data)
                        clean()
                        onFinished()
                    }
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
