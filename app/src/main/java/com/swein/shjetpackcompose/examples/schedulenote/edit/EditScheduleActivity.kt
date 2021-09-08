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

    private val viewModel: EditScheduleViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ThemeUtility.setWindowStatusBarColor(this, getColor(R.color.basic_color_2022))
        ThemeUtility.setSystemBarTheme(this, true)

        setContent {
            EditToDoItemView.ActivityContentView(viewModel)
        }

        initFlow()
    }

    private fun initFlow() {

        lifecycleScope.launch {

//            viewModel.homeViewModelState.collect {
//
//                when (it) {
//                    is HomeViewModelState.Loading -> {
//                        showProgress()
//                    }
//
//                    is HomeViewModelState.Reload -> {
//
//                        recyclerView.post {
//                            adapter.reload(it.list, it.topList)
//                        }
//                        hideProgress()
//                    }
//
//                    is HomeViewModelState.LoadMore -> {
//                        recyclerView.post {
//                            adapter.loadMore(it.list)
//                        }
//                        hideProgress()
//                    }
//
//                    is HomeViewModelState.Error -> {
//                        ILog.debug(TAG, Thread.currentThread().name)
//                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
//                        hideProgress()
//                    }
//
//                    else -> Unit
//                }
//            }
        }

    }
}
