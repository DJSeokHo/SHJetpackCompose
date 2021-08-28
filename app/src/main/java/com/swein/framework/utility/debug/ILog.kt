package com.swein.framework.utility.debug

import android.util.Log

object ILog {

    private const val TAG: String = "ILog ======> "

    fun debug(tag: String, content: Any?) {
        Log.d(TAG, "$tag $content")
    }

}