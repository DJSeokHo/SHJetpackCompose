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

/*
sealed class LiveListViewModelState {

    data class Reload(val list: List<LiveModel>): LiveListViewModelState()
    data class LoadMore(val list: List<LiveModel>): LiveListViewModelState()
    data class Error(val message: String?): LiveListViewModelState()
    object None: LiveListViewModelState()
    object Loading: LiveListViewModelState()
}

class LiveListViewModel: ViewModel() {

    private val _liveListViewModelState = MutableStateFlow<LiveListViewModelState>(LiveListViewModelState.None)
    val liveListViewModelState: StateFlow<LiveListViewModelState> = _liveListViewModelState

    /**
     * 카테고리 0:미분류,11:소통,음악,22:게임,33:스포츠,44:신입BJ,55:모바일
     * 정렬 순서 방식 0:시청자수 많은순, 1:시청자수 적은순, 2:최신 방송순
     */
    fun reload(
        fieldName: String = "ALL",
        keyWord: String = "",
        levKey: String = "",
        catKey: String = "0",
        orderBy: String = "0",
        offset: Int = 0,
        size: Int = 20
    ) = viewModelScope.launch {

        _liveListViewModelState.value = LiveListViewModelState.Loading

        try {
            coroutineScope {

                val liveListResult = async {
                    LiveListService.mainList(fieldName, keyWord, levKey, catKey, orderBy, offset, size)
                }

                val resultLiveList = liveListResult.await()

                _liveListViewModelState.value = LiveListViewModelState.Reload(resultLiveList)

            }
        }
        catch (e: Exception) {
            _liveListViewModelState.value = LiveListViewModelState.Error(e.message)
        }

    }

    fun loadMore(
        fieldName: String = "ALL",
        keyWord: String = "",
        levKey: String = "",
        catKey: String = "0",
        orderBy: String = "0",
        offset: Int = 0,
        size: Int = 20
    ) = viewModelScope.launch {

        _liveListViewModelState.value = LiveListViewModelState.Loading

        try {
            coroutineScope {
                val liveListResult = async {
                    LiveListService.mainList(fieldName, keyWord, levKey, catKey, orderBy, offset, size)
                }

                val resultLiveList = liveListResult.await()

                _liveListViewModelState.value = LiveListViewModelState.LoadMore(resultLiveList)
            }
        }
        catch (e: Exception) {
            _liveListViewModelState.value = LiveListViewModelState.Error(e.message)
        }

    }

}

 */