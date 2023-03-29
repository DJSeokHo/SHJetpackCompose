package com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.models.chatcompletionmodel.request

import com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.models.chatcompletionmodel.ChatCompletionMessageModel
import org.json.JSONArray
import org.json.JSONObject

data class ChatCompletionRequestModel(
    var model: String = "",
    var messages: MutableList<ChatCompletionMessageModel> = mutableListOf(),
    var temperature: Float = 0f,
    var n: Int = 1,
    var stream: Boolean = false
) {

    fun toJSONObject(): JSONObject {

        return JSONObject().apply {
            put("model", model)

            JSONArray().apply {

                for (message in messages) {
                    put(message.toJSONObject())
                }

                put("messages", this)
            }

            put("temperature", temperature)
            put("n", n)
            put("stream", stream)
        }
    }
}
