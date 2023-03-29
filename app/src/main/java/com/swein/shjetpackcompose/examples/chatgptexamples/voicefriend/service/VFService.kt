package com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.service

import com.swein.framework.utility.debug.ILog
import com.swein.framework.utility.okhttp.OKHttpWrapper
import com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.constants.VFWebConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File

object VFService {

    const val TAG = "VFService"

    suspend fun models(): String = withContext(Dispatchers.IO) {

        val url = VFWebConstants.models()
        ILog.debug(TAG, "models: $url")

        val header = mutableMapOf<String, String>()
        header[VFWebConstants.TOKEN_KEY] = VFWebConstants.TOKEN
        val coroutineResponse = OKHttpWrapper.requestGet(url, header)

        val responseString: String = OKHttpWrapper.getStringResponse(coroutineResponse.response)

        OKHttpWrapper.cancelCall(coroutineResponse.call)

        return@withContext responseString
    }

    suspend fun audioTranscriptions(file: File, mediaType: String): String = withContext(Dispatchers.IO) {

        val url = VFWebConstants.audioTranscriptions()
        ILog.debug(TAG, "audioTranscriptions: $url")

        val header = mutableMapOf<String, String>()
        header[VFWebConstants.TOKEN_KEY] = VFWebConstants.TOKEN

        val formData = mutableMapOf<String, String>()
        formData["model"] = "whisper-1"
        formData["language"] = "ko"
        formData["response_format"] = "json"

        val coroutineResponse = OKHttpWrapper.requestPost(url, header, formData, file, mediaType, "file")

        val responseString: String = OKHttpWrapper.getStringResponse(coroutineResponse.response)

        OKHttpWrapper.cancelCall(coroutineResponse.call)

        return@withContext responseString
    }

    suspend fun chatCompletions(jsonObject: JSONObject): String = withContext(Dispatchers.IO) {

        val url = VFWebConstants.chatCompletions()
        ILog.debug(TAG, "chatCompletions: $url")

        val header = mutableMapOf<String, String>()
        header[VFWebConstants.TOKEN_KEY] = VFWebConstants.TOKEN
        val coroutineResponse = OKHttpWrapper.requestPost(url, header, jsonObject = jsonObject)

        val responseString: String = OKHttpWrapper.getStringResponse(coroutineResponse.response)

        OKHttpWrapper.cancelCall(coroutineResponse.call)

        return@withContext responseString
    }


}