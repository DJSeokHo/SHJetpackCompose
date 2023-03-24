package com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.models.chatcompletionmodel.response

import com.swein.framework.utility.parsing.ParsingUtility
import org.json.JSONObject

data class ChatCompletionsResponseModel(
    var id: String = "",
    var obj: String = "",
    var created: Long = 0,
    var model: String = "",
    var usage: ChatCompletionUsageModel = ChatCompletionUsageModel(),
    var choices: MutableList<ChatCompletionChoicesModel> = mutableListOf(),
) {

    fun parsing(jsonObject: JSONObject) {
        id = ParsingUtility.parsingString(jsonObject, "id")
        obj = ParsingUtility.parsingString(jsonObject, "object")
        created = ParsingUtility.parsingLong(jsonObject, "created")
        model = ParsingUtility.parsingString(jsonObject, "model")

        usage = ChatCompletionUsageModel()
        usage.parsing(ParsingUtility.parsingJSONObject(jsonObject, "usage"))

        choices.clear()
        var chatCompletionChoicesModel: ChatCompletionChoicesModel
        val choicesArray = ParsingUtility.parsingJSONArray(jsonObject, "choices")
        for (i in 0 until choicesArray.length()) {
            chatCompletionChoicesModel = ChatCompletionChoicesModel()
            chatCompletionChoicesModel.parsing(choicesArray[i] as JSONObject)
            choices.add(chatCompletionChoicesModel)
        }
    }
}
