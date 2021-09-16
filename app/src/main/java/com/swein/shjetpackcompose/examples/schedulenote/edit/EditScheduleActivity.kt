package com.swein.shjetpackcompose.examples.schedulenote.edit

import android.os.Bundle
import android.view.inputmethod.InputMethodSession
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.swein.easyeventobserver.EventCenter
import com.swein.framework.module.systemimagepicker.SystemPhotoPickManager
import com.swein.framework.utility.date.DateUtility
import com.swein.framework.utility.debug.ILog
import com.swein.framework.utility.window.WindowUtility
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.schedulenote.constants.ScheduleNoteConstants
import com.swein.shjetpackcompose.examples.schedulenote.edit.service.EditScheduleService
import com.swein.shjetpackcompose.examples.schedulenote.edit.view.EditToDoItemView
import com.swein.shjetpackcompose.examples.schedulenote.edit.viewmodel.EditScheduleViewModel
import com.swein.shjetpackcompose.examples.schedulenote.model.ScheduleModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject

class EditScheduleActivity : ComponentActivity() {

    companion object {
        private const val TAG = "EditScheduleActivity"
    }

    private val viewModel by viewModels<EditScheduleViewModel>()
    private val systemPhotoPickManager = SystemPhotoPickManager(this)

    private var scheduleModel: ScheduleModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getBundleExtra("bundle")?.let {
            val scheduleModelJSONObjectString = it.getString("scheduleModel", "")
            if (scheduleModelJSONObjectString != "") {

                scheduleModel = ScheduleModel()
                scheduleModel!!.initWithJSONObject(JSONObject(scheduleModelJSONObjectString))
                viewModel.initWithObject(scheduleModel!!)
            }
        }

        WindowUtility.setWindowStatusBarColor(this, getColor(R.color.basic_color_2022))
        WindowUtility.setSystemBarTheme(this, true)

        setContent {
            EditToDoItemView.ActivityContentView(viewModel = viewModel)
        }

        lifecycleScope.launch {

            val test = async {
                EditScheduleService.loadAll()
//                EditScheduleService.clean()
            }

            val testResult = test.await()

            ILog.debug(TAG, "$testResult")
        }

//        viewModel.contentImage.value = "/storage/emulated/0/Android/data/com.swein.shjetpackcompose/files/Pictures/2021_9_15_12_44_34_3608101991505544178208.jpg"
    }

    fun takePhoto() {
        systemPhotoPickManager.requestPermission {
            it.takePicture(
                true,
                takeUriDelegate = {

                },
                takePathDelegate = { string ->
                    ILog.debug(TAG, "takePathDelegate $string")
                    viewModel.contentImage.value = string
                },
                takeBitmapDelegate = {

                },
                DateUtility.getCurrentDateTimeMillisecondStringWithNoSpace("_")
            )
        }
    }

    fun selectImage() {
        systemPhotoPickManager.requestPermission {

            it.selectPicture(
                true,
                selectedUriDelegate = {

                },
                selectedPathDelegate = { string ->
                    ILog.debug(TAG, "selectedPathDelegate $string")
                    viewModel.contentImage.value = string
                },
                DateUtility.getCurrentDateTimeMillisecondStringWithNoSpace("_")
            )
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
