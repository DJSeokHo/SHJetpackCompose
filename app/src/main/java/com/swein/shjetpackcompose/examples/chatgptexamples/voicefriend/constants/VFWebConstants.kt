package com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.constants

import com.swein.framework.utility.parsing.ParsingUtility
import org.json.JSONObject

object VFWebConstants {

    private const val PROTOCOL = "https://"
    private const val SERVER = "api.openai.com"
    private const val SERVER_VERSION = "/v1"

    private const val DOMAIN = "$PROTOCOL$SERVER$SERVER_VERSION"

    const val TOKEN_KEY = "Authorization"
    const val TOKEN = "Bearer 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"

    fun getResponseJSONObject(responseString: String): JSONObject {
        return JSONObject(responseString)
    }

    fun getDataJSONObject(responseJSONObject: JSONObject): JSONObject {
        return ParsingUtility.parsingJSONObject(responseJSONObject, "data")
    }

    fun models() = "$DOMAIN/models"

    fun audioTranscriptions() = "$DOMAIN/audio/transcriptions"

    fun chatCompletions() = "$DOMAIN/chat/completions"
}