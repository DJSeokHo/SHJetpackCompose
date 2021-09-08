package com.swein.shjetpackcompose.examples.schedulenote.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.swein.framework.utility.theme.ThemeUtility
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.schedulenote.edit.EditScheduleActivity
import com.swein.shjetpackcompose.examples.schedulenote.main.view.ScheduleListView

class ScheduleListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ThemeUtility.setWindowStatusBarColor(this, getColor(R.color.basic_color_2022))
        ThemeUtility.setSystemBarTheme(this, true)

        setContent {
            ScheduleListView.ActivityContentView()
        }

        Intent(this, EditScheduleActivity::class.java).apply {
            startActivity(this)
        }
    }
}
