package com.swein.shjetpackcompose.examples.lazycolumexample.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.examples.lazycolumexample.model.LazyColumnExampleModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LazyColumnExampleViewModel: ViewModel() {

    companion object {
        private const val TAG = "LazyColumnExampleViewModel"
        const val LIMIT = 20
    }


//    val lazyColumnExampleModelList: MutableState<MutableList<LazyColumnExampleModel>> = mutableStateOf(mutableListOf())
    val lazyColumnExampleModelList = mutableStateListOf<LazyColumnExampleModel>()

    var isIO = mutableStateOf(false)

    fun reload() {

        ILog.debug(TAG, "reload")

        viewModelScope.launch {

            isIO.value = true

            try {
                coroutineScope {

                    val list = async {
                        delay(1500)
                        createData(0, LIMIT)
                    }

                    val listResult = list.await()

                    isIO.value = false

                    ILog.debug(TAG, "$listResult")

//                    lazyColumnExampleModelList.value.clear()
//                    lazyColumnExampleModelList.value.addAll(listResult)
//                    ILog.debug(TAG, "reload finished size ??? ${lazyColumnExampleModelList.value.size}")

                    lazyColumnExampleModelList.clear()
                    lazyColumnExampleModelList.addAll(listResult)
                    ILog.debug(TAG, "reload finished size ??? ${lazyColumnExampleModelList.size}")
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                isIO.value = false
            }
        }

    }

    fun loadMore() {

//        ILog.debug(TAG, "loadMore from ${lazyColumnExampleModelList.value.size}")
        ILog.debug(TAG, "loadMore from ${lazyColumnExampleModelList.size}")

        viewModelScope.launch {

            isIO.value = true

            try {
                coroutineScope {

                    val list = async {
                        delay(1500)
//                        createData(lazyColumnExampleModelList.value.size, LIMIT)
                        createData(lazyColumnExampleModelList.size, LIMIT)
                    }

                    val listResult = list.await()

                    isIO.value = false

                    ILog.debug(TAG, "$listResult")

                    if (listResult.isNotEmpty()) {
//                        lazyColumnExampleModelList.value.addAll(listResult)
//                        ILog.debug(TAG, "loadMore finished size ??? ${lazyColumnExampleModelList.value.size}")

                        lazyColumnExampleModelList.addAll(listResult)
                        ILog.debug(TAG, "loadMore finished size ??? ${lazyColumnExampleModelList.size}")
                    }
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                isIO.value = false
            }
        }

    }

    private fun createData(offset: Int, limit: Int): List<LazyColumnExampleModel> {

        val list = mutableListOf<LazyColumnExampleModel>()
        var lazyColumnExampleModel: LazyColumnExampleModel

//        if (offset > 5) {
//            return list
//        }

        for (i in offset until offset + limit) {
            lazyColumnExampleModel = LazyColumnExampleModel()
            lazyColumnExampleModel.index = i
            lazyColumnExampleModel.content = "content $i"
            list.add(lazyColumnExampleModel)
        }

        return list
    }
}