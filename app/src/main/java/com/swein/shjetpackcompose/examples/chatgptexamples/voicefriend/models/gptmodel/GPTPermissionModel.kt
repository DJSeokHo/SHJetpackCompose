package com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.models.gptmodel

import com.swein.framework.utility.parsing.ParsingUtility
import org.json.JSONObject

data class GPTPermissionModel(
    var id: String = "",
    var obj: String = "",
    var created: Long = 0,
    var allowCreateEngine: Boolean = false,
    var allowSampling: Boolean = false,
    var allowLogprobs: Boolean = false,
    var allowSearchIndices: Boolean = false,
    var allowView: Boolean = false,
    var allowFineTuning: Boolean = false,
    var organization: String = "",
    var group: String = "",
    var isBlocking: Boolean = false
) {

    fun parsing(jsonObject: JSONObject) {
        id = ParsingUtility.parsingString(jsonObject, "id")
        obj = ParsingUtility.parsingString(jsonObject, "object")
        created = ParsingUtility.parsingLong(jsonObject, "created")
        allowCreateEngine = ParsingUtility.parsingBoolean(jsonObject, "allow_create_engine")
        allowSampling = ParsingUtility.parsingBoolean(jsonObject, "allow_sampling")
        allowLogprobs = ParsingUtility.parsingBoolean(jsonObject, "allow_logprobs")
        allowSearchIndices = ParsingUtility.parsingBoolean(jsonObject, "allow_search_indices")
        allowView = ParsingUtility.parsingBoolean(jsonObject, "allow_view")
        allowFineTuning = ParsingUtility.parsingBoolean(jsonObject, "allow_fine_tuning")
        organization = ParsingUtility.parsingString(jsonObject, "organization")
        group = ParsingUtility.parsingString(jsonObject, "group")
        isBlocking = ParsingUtility.parsingBoolean(jsonObject, "is_blocking")
    }
}
