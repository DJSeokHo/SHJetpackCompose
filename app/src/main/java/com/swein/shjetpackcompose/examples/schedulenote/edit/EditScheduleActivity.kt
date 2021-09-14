package com.swein.shjetpackcompose.examples.schedulenote.edit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.swein.framework.module.systemimagepicker.SystemPhotoPickManager
import com.swein.framework.utility.date.DateUtility
import com.swein.framework.utility.debug.ILog
import com.swein.framework.utility.theme.ThemeUtility
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.schedulenote.edit.service.EditScheduleService
import com.swein.shjetpackcompose.examples.schedulenote.edit.view.EditToDoItemView
import com.swein.shjetpackcompose.examples.schedulenote.edit.viewmodel.EditScheduleViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONObject

class EditScheduleActivity : ComponentActivity() {

    companion object {
        private const val TAG = "EditScheduleActivity"
    }

    private val viewModel by viewModels<EditScheduleViewModel>()
    private val systemPhotoPickManager = SystemPhotoPickManager(this)

    private var scheduleModelJSONObjectString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getBundleExtra("bundle")?.let {
            scheduleModelJSONObjectString = it.getString("scheduleModelJSONObjectString", "")
            if (scheduleModelJSONObjectString != "") {
                viewModel.initWithJSONObject(JSONObject(scheduleModelJSONObjectString))
            }
        }

        ThemeUtility.setWindowStatusBarColor(this, getColor(R.color.basic_color_2022))
        ThemeUtility.setSystemBarTheme(this, true)

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
