package com.swein.shjetpackcompose.examples.schedulenote.edit.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.examples.schedulenote.model.ScheduleDataModel
import com.swein.shjetpackcompose.examples.schedulenote.viewmodel.ScheduleListViewModelState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kr.co.dotv365.android.framework.utility.parsing.ParsingUtility
import org.json.JSONObject

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

    fun initWithJSONObject(jsonObject: JSONObject? = null) {

        jsonObject?.let {

            uuid.value = ParsingUtility.parsingString(jsonObject, "uuid")
            title.value = ParsingUtility.parsingString(jsonObject, "title")
            content.value = ParsingUtility.parsingString(jsonObject, "content")
            contentImage.value = ParsingUtility.parsingString(jsonObject, "contentImage")
            createDate.value = ParsingUtility.parsingString(jsonObject, "createDate")
            isImportant.value = ParsingUtility.parsingBoolean(jsonObject, "isImportant")
            isUrgent.value = ParsingUtility.parsingBoolean(jsonObject, "isUrgent")
            isFinished.value = ParsingUtility.parsingBoolean(jsonObject, "isFinished")

        }

    }

    fun onSave() {

        viewModelScope.launch {

            isIO.value = true

            try {
                coroutineScope {

                    val insert = async {
                        delay(2000)
                    }

                    val result = insert.await()

                    if (result) {
                        isIO.value = false
                    }

                }
            }
            catch (e: Exception) {
                isIO.value = false
            }

        }

    }
}
