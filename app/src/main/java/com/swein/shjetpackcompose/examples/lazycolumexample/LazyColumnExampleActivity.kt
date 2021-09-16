package com.swein.shjetpackcompose.examples.lazycolumexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.swein.shjetpackcompose.examples.lazycolumexample.view.LazyColumnExampleView
import com.swein.shjetpackcompose.examples.lazycolumexample.viewmodel.LazyColumnExampleViewModel

class LazyColumnExampleActivity : ComponentActivity() {

    private val viewModel by viewModels<LazyColumnExampleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LazyColumnExampleView.ActivityContentView(viewModel = viewModel)
        }

        viewModel.reload()
    }

}
