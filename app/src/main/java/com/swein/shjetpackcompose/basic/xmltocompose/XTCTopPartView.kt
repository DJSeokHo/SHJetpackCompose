package com.swein.shjetpackcompose.basic.xmltocompose

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.framework.extension.compose.color.parse
import com.swein.shjetpackcompose.R

@SuppressLint("ViewConstructor")
class XTCTopPartView(
    context: Context,
    onImageFavoriteClick: (isFavorite: Boolean) -> Unit
): LinearLayout(context) {

    private var imageViewFavorite: ImageView
    private var composeView: ComposeView

    private var isFavorite: Boolean = false

    init {

        inflate(context, R.layout.view_xtc_top_part, this)

        imageViewFavorite = findViewById(R.id.imageViewFavorite)
        composeView = findViewById(R.id.composeView)

        imageViewFavorite.setOnClickListener {

            onImageFavoriteClick(isFavorite)

        }

        // 静态的占位view进行添加
        composeView.setContent {

            Text(
                text = "Monument to Constable Jeremiah Sheehan, 6' 4\" tall of the DMP who went down into a drain at this point to rescue some council workmen who had been overcome with sewer gas and lost his own life!",
                modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(10.dp),
                fontSize = 16.sp,
                color = Color.parse("#666666")
            )
        }

        updateFavorite()
    }

    fun toggleFavorite(isFavorite: Boolean) {
        this.isFavorite = isFavorite

        updateFavorite()
    }

    private fun updateFavorite() {

        imageViewFavorite.setImageResource(
            if (isFavorite) {
                R.mipmap.ti_favorite
            }
            else {
                R.mipmap.ti_un_favorite
            }
        )
    }

}