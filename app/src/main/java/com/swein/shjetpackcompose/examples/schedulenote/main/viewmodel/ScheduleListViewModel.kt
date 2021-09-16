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

    var shouldScrollToTop = false

    fun reload() {

        ILog.debug(TAG, "reload")

        viewModelScope.launch {

            isIO.value = true

            try {
                coroutineScope {

                    val scheduleList = async {
                        EditScheduleService.load(0, LIMIT)
                    }

                    val scheduleListResult = scheduleList.await()

                    isIO.value = false

                    ILog.debug(TAG, "$scheduleListResult")

                    list.clear()
                    list.addAll(scheduleListResult)

                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                isIO.value = false
            }
        }

    }

    fun loadMore() {

        ILog.debug(TAG, "loadMore from ${list.size}")

        viewModelScope.launch {

            isIO.value = true

            try {
                coroutineScope {

                    val scheduleList = async {
                        EditScheduleService.load(list.size, LIMIT)
                    }

                    val scheduleListResult = scheduleList.await()

                    ILog.debug(TAG, "$scheduleListResult")

                    isIO.value = false

                    if (scheduleListResult.isEmpty()) {
                        ILog.debug(TAG, "no more")
                        return@coroutineScope
                    }

                    list.addAll(scheduleListResult)
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                isIO.value = false
            }
        }

    }

}
