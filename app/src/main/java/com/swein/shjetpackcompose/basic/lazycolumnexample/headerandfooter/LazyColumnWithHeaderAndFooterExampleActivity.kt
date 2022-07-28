package com.swein.shjetpackcompose.basic.lazycolumnexample.headerandfooter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.swein.shjetpackcompose.basic.lazycolumnexample.headerandfooter.viewmodel.LazyColumnWithHeaderAndFooterExampleViewModel

class LazyColumnWithHeaderAndFooterExampleActivity : ComponentActivity() {

    private val viewModel: LazyColumnWithHeaderAndFooterExampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {
            LazyColumnWithHeaderAndFooterExampleControllerView(
                viewModel = viewModel
            )
        }
    }
}

