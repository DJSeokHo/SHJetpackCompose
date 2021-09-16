package com.swein.shjetpackcompose.examples.schedulenote.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.swein.easyeventobserver.EventCenter
import com.swein.framework.utility.debug.ILog
import com.swein.framework.utility.window.WindowUtility
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.schedulenote.constants.ScheduleNoteConstants
import com.swein.shjetpackcompose.examples.schedulenote.edit.EditScheduleActivity
import com.swein.shjetpackcompose.examples.schedulenote.main.view.ScheduleListView
import com.swein.shjetpackcompose.examples.schedulenote.main.viewmodel.ScheduleListViewModel
import com.swein.shjetpackcompose.examples.schedulenote.model.ScheduleModel
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

        initESS()

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

    private fun initESS() {
        EventCenter.addEventObserver(ScheduleNoteConstants.ESS_REFRESH_SCHEDULE_LIST, this, object : EventCenter.EventRunnable {
            override fun run(arrow: String, poster: Any, data: MutableMap<String, Any>?) {
                viewModel.shouldScrollToTop = true
                viewModel.reload()
            }
        })
    }

    fun openDetail(scheduleModel: ScheduleModel) {

        ILog.debug(TAG, "$scheduleModel")

        Intent(this@ScheduleListActivity, EditScheduleActivity::class.java).apply {

            val bundle = Bundle()
            bundle.putString("scheduleModel", scheduleModel.toJSONObject().toString())
            putExtra("bundle", bundle)
            startActivity(this)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventCenter.removeAllObserver(this)
    }

    override fun onBackPressed() {
        finish()
    }
}
