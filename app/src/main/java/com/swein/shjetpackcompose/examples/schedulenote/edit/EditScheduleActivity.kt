package com.swein.shjetpackcompose.examples.schedulenote.edit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.swein.framework.module.systemimagepicker.SystemPhotoPickManager
import com.swein.framework.utility.debug.ILog
import com.swein.framework.utility.theme.ThemeUtility
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.schedulenote.edit.view.EditToDoItemView
import com.swein.shjetpackcompose.examples.schedulenote.edit.viewmodel.EditScheduleViewModel
import kotlinx.coroutines.launch

class EditScheduleActivity : ComponentActivity() {

    private val viewModel by viewModels<EditScheduleViewModel>()
    private val systemPhotoPickManager = SystemPhotoPickManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ThemeUtility.setWindowStatusBarColor(this, getColor(R.color.basic_color_2022))
        ThemeUtility.setSystemBarTheme(this, true)

        viewModel.initWithJSONObject()

        setContent {
            EditToDoItemView.ActivityContentView(viewModel = viewModel)
        }
    }

    fun takePhoto() {
        systemPhotoPickManager.requestPermission {
            it.takePictureWithFilePath(true) { filePath ->
                viewModel.contentImage.value = filePath
            }
        }
    }

    fun selectImage() {
        systemPhotoPickManager.requestPermission {

            it.selectPathPicture(true) { filePath ->
                viewModel.contentImage.value = filePath
            }
        }
    }

    override fun onBackPressed() {

        super.onBackPressed()
    }
}
