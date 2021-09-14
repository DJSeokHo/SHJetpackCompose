package com.swein.framework.extension.string

import java.net.URLDecoder
import java.net.URLEncoder

fun String.urlEncode(enc: String = "UTF-8"): String {
    return URLEncoder.encode(this, enc)
}

fun String.urlDecode(enc: String = "UTF-8"): String {
    return URLDecoder.decode(this, enc)
}