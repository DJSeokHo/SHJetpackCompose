package com.swein.shjetpackcompose.examples.viewholderexample

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

object ToolBarView {

    @Composable
    fun TopToolBarView(
        modifier: Modifier = Modifier,
        startImageResource: Int = -1,
        title: String = "",
        endImageResource: Int = -1,
        onStartClick: (() -> Unit)? = null,
        onEndClick: (() -> Unit)? = null
    ) {

        Surface(
            color = Color.Gray,
            modifier = modifier.fillMaxWidth().height(60.dp),
            elevation = 10.dp
        ) {

            val constraints = constraints()

            ConstraintLayout(
                constraints,
                modifier = modifier.fillMaxSize()
            ) {

                if (startImageResource != -1) {

                    IconButton(
                        onClick = {
                            onStartClick?.let {
                                it()
                            }
                        },
                        modifier = modifier.layoutId("startImage").clip(CircleShape).background(Color.Transparent),

                        ) {
                        Icon(
                            painter = painterResource(id = startImageResource),
                            contentDescription = "print",
                            modifier = modifier.fillMaxSize().padding(8.dp),
                            tint = Color.White
                        )
                    }
                }

                if (title != "") {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = modifier.fillMaxSize().padding(start = 80.dp, end = 80.dp).wrapContentHeight(CenterVertically).wrapContentWidth(CenterHorizontally)
                    )
                }

                if (endImageResource != -1) {

                    IconButton(
                        onClick = {
                            onEndClick?.let {
                                it()
                            }
                        },
                        modifier = modifier.layoutId("endImage").clip(CircleShape).background(Color.Transparent),

                        ) {
                        Icon(
                            painter = painterResource(id = endImageResource),
                            contentDescription = "print",
                            modifier = modifier.fillMaxSize().padding(8.dp),
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

    private fun constraints(): ConstraintSet {
        return ConstraintSet {
            val startImage = createRefFor("startImage")
            val endImage = createRefFor("endImage")

            constrain(startImage) {
                centerVerticallyTo(parent)
                start.linkTo(parent.start, margin = 10.dp)
            }

            constrain(endImage) {
                centerVerticallyTo(parent)
                end.linkTo(parent.end, margin = 10.dp)
            }

        }
    }

}

