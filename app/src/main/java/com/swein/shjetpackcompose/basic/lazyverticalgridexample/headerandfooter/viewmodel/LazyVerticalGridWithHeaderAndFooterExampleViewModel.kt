package com.swein.shjetpackcompose.basic.lazyverticalgridexample.headerandfooter.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.basic.lazyverticalgridexample.headerandfooter.dummyserver.LazyVerticalGridWithHeaderAndFooterExampleDummyServer
import com.swein.shjetpackcompose.basic.lazyverticalgridexample.headerandfooter.model.ListItemData
import kotlinx.coroutines.*

const val GRID_LIST_HEADER_COUNT = 1
const val GRID_LIST_FOOTER_COUNT = 1

class LazyVerticalGridWithHeaderAndFooterExampleViewModelState {

    val loading = mutableStateOf(false)
    fun toggleLoading(isLoading: Boolean) {
        loading.value = isLoading
    }
}

class LazyVerticalGridWithHeaderAndFooterExampleViewModel: ViewModel() {

    val state = LazyVerticalGridWithHeaderAndFooterExampleViewModelState()
    val list = mutableStateListOf<ListItemData>()

    fun fetchData(offset: Int, limit: Int) = viewModelScope.launch {

        ILog.debug("????", "fetchData $offset $limit")

        state.toggleLoading(true)

        try {
            coroutineScope {

                val fetch = async(Dispatchers.IO) {
                    delay(500)
                    LazyVerticalGridWithHeaderAndFooterExampleDummyServer.fetchDataFromDummyServer(offset, limit)
                }

                val tempList = fetch.await()

                state.toggleLoading(false)

                if (offset == 0) {
                    // reload
                    list.clear()

                    // add header
                    list.add(
                        ListItemData(type = ListItemData.Type.HEADER)
                    )
                }

                // remove footer
                if (list[list.size - 1].type == ListItemData.Type.FOOTER) {
                    list.removeLast()
                }

                list.addAll(tempList)

                // add footer
                if (list[list.size - 1].type != ListItemData.Type.FOOTER) {
                    list.add(
                        ListItemData(type = ListItemData.Type.FOOTER)
                    )
                }
            }
        }
        catch (e: Exception) {
            state.toggleLoading(false)
            e.printStackTrace()
        }
    }
}
