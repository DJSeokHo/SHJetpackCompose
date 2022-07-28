package com.swein.shjetpackcompose.basic.lazyverticalgridexample.headerandfooter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.swein.shjetpackcompose.basic.lazyverticalgridexample.headerandfooter.viewmodel.LazyVerticalGridWithHeaderAndFooterExampleViewModel

class LazyVerticalGridWithHeaderAndFooterExampleActivity : ComponentActivity() {

    private val viewModel: LazyVerticalGridWithHeaderAndFooterExampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            LazyVerticalGridWithHeaderAndFooterExampleControllerView(
                viewModel = viewModel
            )

        }
    }
}