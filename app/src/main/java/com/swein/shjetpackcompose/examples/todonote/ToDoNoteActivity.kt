package com.swein.shjetpackcompose.examples.todonote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.swein.framework.utility.theme.ThemeUtility
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.todonote.view.ToDoNoteView

class ToDoNoteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ThemeUtility.setWindowStatusBarColor(this, getColor(R.color.basic_color_2022))
        ThemeUtility.setSystemBarTheme(this, true)

        setContent {

            ToDoNoteView.ActivityContentView { _ ->

            }

        }
    }
}
