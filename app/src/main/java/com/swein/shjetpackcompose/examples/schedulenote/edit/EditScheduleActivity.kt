package com.swein.shjetpackcompose.examples.schedulenote.edit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import com.swein.framework.module.systemimagepicker.SystemPhotoPickManager
import com.swein.framework.utility.debug.ILog
import com.swein.framework.utility.window.WindowUtility
import com.swein.shjetpackcompose.application.ui.theme.ColorC57644
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

        WindowUtility.setStatusBarColor(this, ColorC57644.toArgb())
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

            it.takePictureWithFilePath(true) { imagePath ->
                ILog.debug(TAG, "takePathDelegate $imagePath")
                viewModel.contentImage.value = imagePath
            }

        }
    }

    fun selectImage() {
        systemPhotoPickManager.requestPermission {

            it.selectPathPicture(true) { imagePath ->
                ILog.debug(TAG, "selectedPathDelegate $imagePath")
                viewModel.contentImage.value = imagePath
            }

        }
    }

}
