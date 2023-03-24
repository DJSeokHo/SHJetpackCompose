package com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.models.gptmodel

import com.swein.framework.utility.parsing.ParsingUtility
import org.json.JSONObject

data class GPTModel(
    var id: String = "",
    var obj: String = "",
    var created: Long = 0,
    var ownedBy: String = "",
    var permissions: MutableList<GPTPermissionModel> = mutableListOf(),
    var root: String = "",
    var parent: String = ""
) {

    fun parsing(jsonObject: JSONObject) {
        id = ParsingUtility.parsingString(jsonObject, "id")
        obj = ParsingUtility.parsingString(jsonObject, "object")
        created = ParsingUtility.parsingLong(jsonObject, "created")
        ownedBy = ParsingUtility.parsingString(jsonObject, "owned_by")

        permissions.clear()
        var permissionModel: GPTPermissionModel
        val permissionArray = ParsingUtility.parsingJSONArray(jsonObject, "permission")
        for (i in 0 until permissionArray.length()) {
            permissionModel = GPTPermissionModel()
            permissionModel.parsing(permissionArray[i] as JSONObject)
            permissions.add(permissionModel)
        }
        root = ParsingUtility.parsingString(jsonObject, "root")
        parent = ParsingUtility.parsingString(jsonObject, "parent")
    }
}
