package com.swein.shjetpackcompose.examples.schedulenote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swein.shjetpackcompose.examples.schedulenote.model.ScheduleModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ScheduleListViewModelState {

    data class Reload(val list: List<ScheduleModel>): ScheduleListViewModelState()
    data class LoadMore(val list: List<ScheduleModel>): ScheduleListViewModelState()
    data class Error(val message: String?): ScheduleListViewModelState()
    object None: ScheduleListViewModelState()
    object Loading: ScheduleListViewModelState()
}

class ScheduleListViewModel: ViewModel() {

    companion object {
        private const val LIMIT = 20
    }

    private val _scheduleListViewModelState = MutableStateFlow<ScheduleListViewModelState>(ScheduleListViewModelState.None)
    val liveListViewModelState: StateFlow<ScheduleListViewModelState> = _scheduleListViewModelState


    fun reload(
        offset: Int = 0,
        size: Int = LIMIT
    ) = viewModelScope.launch {

        _scheduleListViewModelState.value = ScheduleListViewModelState.Loading

        try {
            coroutineScope {

                val liveListResult = async {
//                    LiveListService.mainList(fieldName, keyWord, levKey, catKey, orderBy, offset, size)
                }

                val resultLiveList = liveListResult.await()

//                _liveListViewModelState.value = ToDoViewModelState.Reload(resultLiveList)

            }
        }
        catch (e: Exception) {
            _scheduleListViewModelState.value = ScheduleListViewModelState.Error(e.message)
        }

    }

    fun loadMore(
        offset: Int = 0,
        size: Int = LIMIT
    ) = viewModelScope.launch {

        _scheduleListViewModelState.value = ScheduleListViewModelState.Loading

        try {
            coroutineScope {
                val liveListResult = async {
//                    LiveListService.mainList(fieldName, keyWord, levKey, catKey, orderBy, offset, size)
                }

                val resultLiveList = liveListResult.await()

//                _liveListViewModelState.value = ToDoViewModelState.LoadMore(resultLiveList)
            }
        }
        catch (e: Exception) {
            _scheduleListViewModelState.value = ScheduleListViewModelState.Error(e.message)
        }

    }

    fun update() = viewModelScope.launch {

    }

    fun delete() = viewModelScope.launch {

    }

}
