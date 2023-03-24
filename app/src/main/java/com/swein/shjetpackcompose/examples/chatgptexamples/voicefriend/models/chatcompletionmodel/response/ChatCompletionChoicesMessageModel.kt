package com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.models.chatcompletionmodel.response

import com.swein.framework.utility.parsing.ParsingUtility
import org.json.JSONObject

data class ChatCompletionChoicesMessageModel(
    var role: String = "",
    var content: String = ""
) {
    fun parsing(jsonObject: JSONObject) {
        role = ParsingUtility.parsingString(jsonObject, "role")
        content = ParsingUtility.parsingString(jsonObject, "content")
    }
}
