package com.swein.shjetpackcompose.examples.schedulenote.main.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.examples.schedulenote.edit.service.EditScheduleService
import com.swein.shjetpackcompose.examples.schedulenote.model.ScheduleModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ScheduleListViewModel: ViewModel() {

    companion object {
        private const val TAG = "ScheduleListViewModel"
        private const val LIMIT = 20
    }


    var list = mutableStateListOf<ScheduleModel>()

    var isIO = mutableStateOf(false)



    fun reload(offset: Int = 0, size: Int = LIMIT) {

        isIO.value = true

        viewModelScope.launch {

            isIO.value = true

            try {
                coroutineScope {

                    val scheduleList = async {
                        EditScheduleService.load(offset, size)
                    }

                    val scheduleListResult = scheduleList.await()

                    ILog.debug(TAG, "$scheduleListResult")

                    list.clear()
                    list.addAll(scheduleListResult)

                    isIO.value = false

                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                isIO.value = false
            }
        }

    }

    fun loadMore(offset: Int, size: Int = LIMIT) {

        isIO.value = true

        viewModelScope.launch {

            isIO.value = true

            try {
                coroutineScope {

                    val scheduleList = async {
                        EditScheduleService.load(offset, size)
                    }

                    val scheduleListResult = scheduleList.await()

                    ILog.debug(TAG, "$scheduleListResult")

                    list.addAll(scheduleListResult)

                    isIO.value = false

                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                isIO.value = false
            }
        }

    }

}
