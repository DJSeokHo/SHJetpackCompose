package com.swein.shjetpackcompose.basic.lazycolumnexample.headerandfooter.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.basic.lazycolumnexample.headerandfooter.dummyserver.LazyColumnWithHeaderAndFooterExampleDummyServer
import com.swein.shjetpackcompose.basic.lazycolumnexample.headerandfooter.model.LCTestDataModel
import kotlinx.coroutines.*

class LazyColumnWithHeaderAndFooterExampleViewModelState {

    val loading = mutableStateOf(false)
    fun toggleLoading(isLoading: Boolean) {
        loading.value = isLoading
    }
}

class LazyColumnWithHeaderAndFooterExampleViewModel: ViewModel() {

    val state = LazyColumnWithHeaderAndFooterExampleViewModelState()
    val list = mutableStateListOf<LCTestDataModel>()

    fun fetchData(offset: Int, limit: Int) = viewModelScope.launch {

        ILog.debug("????", "fetchData $offset $limit")

        state.toggleLoading(true)

        try {
            coroutineScope {

                val fetch = async(Dispatchers.IO) {
                    delay(500)
                    LazyColumnWithHeaderAndFooterExampleDummyServer.fetchDataFromDummyServer(offset, limit)
                }

                val tempList = fetch.await()

                state.toggleLoading(false)

                if (offset == 0) {
                    // reload
                    list.clear()
                }

                list.addAll(tempList)
            }
        }
        catch (e: Exception) {
            state.toggleLoading(false)
            e.printStackTrace()
        }
    }
}
