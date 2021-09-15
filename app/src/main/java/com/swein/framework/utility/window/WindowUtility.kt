package com.swein.framework.utility.window

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.view.WindowManager

object WindowUtility {

    /**
     * hide system top status bar and bottom navigation button bar
     */
    fun hideSystemUI(activity: Activity) {
        val decorView = activity.window.decorView
        val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
        decorView.systemUiVisibility = uiOptions
    }

    /**
     * show system top status bar and bottom navigation button bar
     */
    fun showSystemUI(activity: Activity) {
        val decorView = activity.window.decorView
        val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
        decorView.systemUiVisibility = uiOptions
    }

    /**
     * must > API 19
     * put this before setContentView()
     *
     * and add
     * android:fitsSystemWindows="true"
     * to your root layout of xml file
     *
     *
     * @param activity
     * @param color
     */
    fun setWindowStatusBarColor(activity: Activity, color: Int) {
        val window = activity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    fun setSystemBarTheme(pActivity: Activity, pIsDark: Boolean) {
        val lFlags = pActivity.window.decorView.systemUiVisibility
        pActivity.window.decorView.systemUiVisibility =
            if (pIsDark) lFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() else lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    fun isDarkModeOn(context: Context): Boolean {
        val currentNightMode = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    fun fullScreenWithTransparentStatusBar(activity: Activity) {
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}