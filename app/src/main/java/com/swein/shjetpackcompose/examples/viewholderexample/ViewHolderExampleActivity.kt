package com.swein.shjetpackcompose.examples.viewholderexample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.R

class ViewHolderExampleActivity : ComponentActivity() {

    companion object {
        const val TAG = "ViewHolderExampleActivity"

        fun startFrom(context: Context) {
            Intent(context, ViewHolderExampleActivity::class.java).apply {
                context.startActivity(this)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Scaffold(modifier = Modifier.fillMaxSize(), backgroundColor = Color.LightGray) {

                ToolBarView.TopToolBarView(
                    startImageResource = R.mipmap.ti_back,
                    title = "1231231aaaa1231231aaaa1231231aaaa1231231aaaa1231231aaaa1231231aaaa1231231aaaa1231231aaaa",
                    endImageResource = R.mipmap.ti_close,
                    onStartClick = {
                        ILog.debug(TAG, "start click")
                    },
                    onEndClick = {
                        ILog.debug(TAG, "end click")
                    }
                )

            }

        }
    }
}

@Preview(showBackground = true, name = "ViewHolderExampleActivityPreview")
@Composable
fun ViewHolderExampleActivityPreview() {

    Scaffold(modifier = Modifier.fillMaxSize(), backgroundColor = Color.LightGray) {

        ToolBarView.TopToolBarView(
            startImageResource = R.mipmap.ti_back,
            title = "1231231aaaa1231231aaaa1231231aaaa1231231aaaa1231231aaaa1231231aaaa1231231aaaa1231231aaaa",
            endImageResource = R.mipmap.ti_close,
            onStartClick = {
                ILog.debug(ViewHolderExampleActivity.TAG, "start click")
            },
            onEndClick = {
                ILog.debug(ViewHolderExampleActivity.TAG, "end click")
            }
        )

    }

}