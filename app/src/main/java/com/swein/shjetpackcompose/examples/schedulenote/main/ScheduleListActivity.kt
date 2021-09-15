package com.swein.shjetpackcompose.examples.schedulenote.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.swein.framework.utility.window.WindowUtility
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.schedulenote.edit.EditScheduleActivity
import com.swein.shjetpackcompose.examples.schedulenote.main.view.ScheduleListView
import com.swein.shjetpackcompose.examples.schedulenote.main.viewmodel.ScheduleListViewModel
import kotlinx.coroutines.launch

class ScheduleListActivity : ComponentActivity() {

    companion object {
        private const val TAG = "ScheduleListActivity"
    }

    private val viewModel by viewModels<ScheduleListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowUtility.setWindowStatusBarColor(this, getColor(R.color.basic_color_2022))
        WindowUtility.setSystemBarTheme(this, true)

        setContent {
            ScheduleListView.ActivityContentView(viewModel = viewModel, onToolBarEndClick = {

                Intent(this@ScheduleListActivity, EditScheduleActivity::class.java).apply {
                    startActivity(this)
                }

            })
        }

        lifecycleScope.launch {
            viewModel.reload()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        finish()
    }
}
