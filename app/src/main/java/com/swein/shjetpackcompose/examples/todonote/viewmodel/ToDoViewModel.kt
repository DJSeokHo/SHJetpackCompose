package com.swein.shjetpackcompose.examples.todonote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swein.shjetpackcompose.examples.todonote.model.ToDoItemDataModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ToDoViewModelState {

    data class Reload(val list: List<ToDoItemDataModel>): ToDoViewModelState()
    data class LoadMore(val list: List<ToDoItemDataModel>): ToDoViewModelState()
    data class Error(val message: String?): ToDoViewModelState()
    object None: ToDoViewModelState()
    object Loading: ToDoViewModelState()
}

class ToDoViewModel: ViewModel() {

    companion object {
        private const val LIMIT = 20
    }

    private val _liveListViewModelState = MutableStateFlow<ToDoViewModelState>(ToDoViewModelState.None)
    val liveListViewModelState: StateFlow<ToDoViewModelState> = _liveListViewModelState


    fun reload(
        offset: Int = 0,
        size: Int = LIMIT
    ) = viewModelScope.launch {

        _liveListViewModelState.value = ToDoViewModelState.Loading

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
            _liveListViewModelState.value = ToDoViewModelState.Error(e.message)
        }

    }

    fun loadMore(
        offset: Int = 0,
        size: Int = LIMIT
    ) = viewModelScope.launch {

        _liveListViewModelState.value = ToDoViewModelState.Loading

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
            _liveListViewModelState.value = ToDoViewModelState.Error(e.message)
        }

    }

    fun update() = viewModelScope.launch {

    }

    fun delete() = viewModelScope.launch {

    }

}
