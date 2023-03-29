package com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.models.chatcompletionmodel.response

import com.swein.framework.utility.parsing.ParsingUtility
import com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.models.chatcompletionmodel.ChatCompletionMessageModel
import org.json.JSONObject

data class ChatCompletionChoicesModel(
    var index: Int = 0,
    var finishReason: String = "",
    var message: ChatCompletionMessageModel = ChatCompletionMessageModel()
) {
    fun parsing(jsonObject: JSONObject) {
        index = ParsingUtility.parsingInt(jsonObject, "index")
        finishReason = ParsingUtility.parsingString(jsonObject, "finish_reason")

        message = ChatCompletionMessageModel()
        message.parsing(ParsingUtility.parsingJSONObject(jsonObject, "message"))
    }
}
