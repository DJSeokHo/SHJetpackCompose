package com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swein.framework.utility.debug.ILog
import com.swein.framework.utility.parsing.ParsingUtility
import com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.models.chatcompletionmodel.ChatCompletionMessageModel
import com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.models.chatcompletionmodel.response.ChatCompletionsResponseModel
import com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.models.gptmodel.GPTModel
import com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.service.VFService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.File

class VFViewModelState {

    val loading = mutableStateOf(false)
    fun toggleLoading(isLoading: Boolean) {
        loading.value = isLoading
    }

    val error = mutableStateOf("")
    fun toggleError(errorMessage: String) {
        error.value = errorMessage
    }

    val recording = mutableStateOf(false)
    fun toggleRecording(isRecording: Boolean) {
        recording.value = isRecording
    }
}

class VFViewModel: ViewModel() {

    val state = VFViewModelState()

    val chatResponse = mutableStateOf("Hello")

    val inputText = mutableStateOf("")
    var recordFile: File? = null

    private val gptModelList = mutableListOf<GPTModel>()
    val messageRecords = mutableListOf<ChatCompletionMessageModel>()


    fun models(onSuccess: () -> Unit) = viewModelScope.launch(Dispatchers.Main) {

        state.toggleLoading(true)
        state.toggleError("")

        try {

            coroutineScope {

                val task = async(Dispatchers.IO) {

                    VFService.models()
                }

                val response = JSONObject(task.await())

                gptModelList.clear()

                var gptModel: GPTModel

                val dataArray = ParsingUtility.parsingJSONArray(response, "data")
                for (i in 0 until dataArray.length()) {
                    gptModel = GPTModel()
                    gptModel.parsing(dataArray[i] as JSONObject)
                    gptModelList.add(gptModel)
                }

                state.toggleLoading(false)

                onSuccess()
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            ILog.debug("???", e.message)
            state.toggleLoading(false)
            state.toggleError(e.message.toString())
        }

    }

    fun audioTranscriptions(onSuccess: () -> Unit) = viewModelScope.launch(Dispatchers.Main) {

        recordFile?.let { recordFile ->

            state.toggleLoading(true)
            state.toggleError("")

            try {

                coroutineScope {

                    val task = async(Dispatchers.IO) {

                        VFService.audioTranscriptions(recordFile, "audio/mp3")
                    }

                    val responseString = task.await()

                    val response = JSONObject(responseString)

                    inputText.value = ParsingUtility.parsingString(response, "text")

                    state.toggleLoading(false)

                    onSuccess()
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                ILog.debug("???", e.message)
                state.toggleLoading(false)
                state.toggleError(e.message.toString())
            }

        }

    }

    fun chatCompletions(jsonObject: JSONObject, onSuccess: () -> Unit) = viewModelScope.launch(Dispatchers.Main) {

        state.toggleLoading(true)
        state.toggleError("")

        try {

            coroutineScope {

                val task = async(Dispatchers.IO) {

                    VFService.chatCompletions(jsonObject)
                }

                val responseString = task.await()

                val response = JSONObject(responseString)

                ILog.debug("???", response)

                val model = ChatCompletionsResponseModel()
                model.parsing(response)

                if (model.choices.isNotEmpty()) {
                    chatResponse.value = model.choices[0].message.content
                    messageRecords.add(model.choices[0].message)
                }

                state.toggleLoading(false)

                onSuccess()
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            ILog.debug("???", e.message)
            state.toggleLoading(false)
            state.toggleError(e.message.toString())
        }

    }

}