package com.swein.shjetpackcompose.examples.schedulenote.edit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.swein.framework.utility.theme.ThemeUtility
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.schedulenote.edit.view.EditToDoItemView
import com.swein.shjetpackcompose.examples.schedulenote.edit.viewmodel.EditScheduleViewModel
import kotlinx.coroutines.launch

class EditScheduleActivity : ComponentActivity() {

    private val viewModel by viewModels<EditScheduleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ThemeUtility.setWindowStatusBarColor(this, getColor(R.color.basic_color_2022))
        ThemeUtility.setSystemBarTheme(this, true)

        viewModel.initWithJSONObject()

        setContent {
            EditToDoItemView.ActivityContentView(viewModel)
        }
    }

    fun pickImage() {

    }

    override fun onBackPressed() {

        finish()
    }
}
