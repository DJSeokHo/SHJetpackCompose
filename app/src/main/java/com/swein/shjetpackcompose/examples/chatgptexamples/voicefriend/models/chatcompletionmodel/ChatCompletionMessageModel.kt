package com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.models.chatcompletionmodel

import com.swein.framework.utility.parsing.ParsingUtility
import org.json.JSONObject

data class ChatCompletionMessageModel(
    var role: String = "",
    var content: String = ""
) {
    fun parsing(jsonObject: JSONObject) {
        role = ParsingUtility.parsingString(jsonObject, "role")
        content = ParsingUtility.parsingString(jsonObject, "content")
    }

    fun toJSONObject(): JSONObject {

        return JSONObject().apply {
            put("role", role)
            put("content", content)
        }
    }
}
