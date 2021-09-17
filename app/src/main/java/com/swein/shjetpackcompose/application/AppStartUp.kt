package com.swein.shjetpackcompose.application

import android.content.Context
import androidx.startup.Initializer
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.examples.schedulenote.database.DatabaseManager

class AppStartUp: Initializer<Unit> {

    companion object {
        private const val TAG = "AppStartUpExample"
    }
    override fun create(context: Context) {
        ILog.debug(TAG, "create")

//        DatabaseManager.init(context)
        DatabaseManager.migration(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        ILog.debug(TAG, "dependencies")
        // No dependencies on other libraries.
        return emptyList()
    }
}