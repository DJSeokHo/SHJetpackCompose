package com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.models.chatcompletionmodel.response

import com.swein.framework.utility.parsing.ParsingUtility
import org.json.JSONObject

data class ChatCompletionUsageModel(
    var promptTokens: Int = 0,
    var completionTokens: Int = 0,
    var totalTokens: Int = 0
) {

    fun parsing(jsonObject: JSONObject) {
        promptTokens = ParsingUtility.parsingInt(jsonObject, "prompt_tokens")
        completionTokens = ParsingUtility.parsingInt(jsonObject, "completion_tokens")
        totalTokens = ParsingUtility.parsingInt(jsonObject, "total_tokens")
    }
}
