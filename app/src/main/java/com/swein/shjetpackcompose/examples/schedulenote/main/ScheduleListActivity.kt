package com.swein.shjetpackcompose.examples.schedulenote.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.swein.framework.utility.window.WindowUtility
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.schedulenote.edit.EditScheduleActivity
import com.swein.shjetpackcompose.examples.schedulenote.main.view.ScheduleListView
import com.swein.shjetpackcompose.examples.schedulenote.main.viewmodel.ScheduleListViewModel
import kotlinx.coroutines.launch

class ScheduleListActivity : ComponentActivity() {

    companion object {
        private const val TAG = "ScheduleListActivity"
    }

    private val viewModel by viewModels<ScheduleListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowUtility.setWindowStatusBarColor(this, getColor(R.color.basic_color_2022))
        WindowUtility.setSystemBarTheme(this, true)

        setContent {
            ScheduleListView.ActivityContentView(viewModel = viewModel, onToolBarEndClick = {

                Intent(this@ScheduleListActivity, EditScheduleActivity::class.java).apply {
                    startActivity(this)
                }

            })
        }

        lifecycleScope.launch {
            viewModel.reload()
        }
    }

//    private fun initList() {
//
//        swipeRefreshLayout.setOnRefreshListener {
//
//            reload()
//
//            swipeRefreshLayout.isRefreshing = false
//        }
//
//        layoutManager = LinearLayoutManager(context)
//        recyclerView.layoutManager = layoutManager
//
//        adapter = LiveListAdapter()
//
//        adapter.onLoadMore = {
//            // load more
//            loadMore()
//        }
//
//        recyclerView.adapter = adapter
//
//        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                if (!adapter.isBig) {
//                    return
//                }
//
//                if (dy > 0) {
//                    EventCenter.sendEvent(ESSArrows.LIVE_LIST_GO_DOWN, this, null)
//                }
//                else {
//                    EventCenter.sendEvent(ESSArrows.LIVE_LIST_GO_UP, this, null)
//                }
//            }
//
//        })
//    }
//
//    fun reload() {
//        viewModel.reload(catKey = selectedCategory.categoryCode, orderBy = orderType.orderCode)
//    }
//
//    private fun loadMore() {
//        viewModel.loadMore(offset = adapter.itemCount, catKey = selectedCategory.categoryCode, orderBy = orderType.orderCode)
//    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBackPressed() {
        finish()
    }
}
