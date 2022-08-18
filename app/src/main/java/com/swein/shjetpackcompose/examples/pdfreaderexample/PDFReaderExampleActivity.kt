package com.swein.shjetpackcompose.examples.pdfreaderexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.swein.shjetpackcompose.examples.pdfreaderexample.viewmodel.PDFReaderViewModel

class PDFReaderExampleActivity : ComponentActivity() {

    private val viewModel: PDFReaderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            PDFReaderExampleControllerView(viewModel)

        }
    }
}