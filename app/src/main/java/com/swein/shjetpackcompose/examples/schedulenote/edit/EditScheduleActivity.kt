package com.swein.shjetpackcompose.examples.schedulenote.edit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import com.swein.framework.module.systemimagepicker.SystemPhotoPickManager
import com.swein.framework.utility.date.DateUtility
import com.swein.framework.utility.debug.ILog
import com.swein.framework.utility.window.WindowUtility
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.schedulenote.edit.service.EditScheduleService
import com.swein.shjetpackcompose.examples.schedulenote.edit.view.EditToDoItemView
import com.swein.shjetpackcompose.examples.schedulenote.edit.viewmodel.EditScheduleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class EditScheduleActivity : ComponentActivity() {

    companion object {
        private const val TAG = "EditScheduleActivity"
    }

    private val viewModel by viewModels<EditScheduleViewModel>()
    private val systemPhotoPickManager = SystemPhotoPickManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent.getBundleExtra("bundle")?.let {
            val uuid = it.getString("uuid", "")
            if (uuid != "") {
                viewModel.loadSchedule(uuid)
            }
        }

        WindowUtility.setStatusBarColor(this, getColor(R.color.basic_color_2022))
        WindowUtility.setStateBarToDarkTheme(this)

        setContent {
            EditToDoItemView.ActivityContentView(viewModel = viewModel)
        }

        lifecycleScope.launch(Dispatchers.Main) {

            whenCreated {

                val test = async {
                    EditScheduleService.loadAll()
//                EditScheduleService.clean()
                }

                val testResult = test.await()

                ILog.debug(TAG, "$testResult")

            }

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
