package com.swein.shjetpackcompose.examples.schedulenote.commonpart

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.application.ui.theme.BlackTransparent30
import com.swein.shjetpackcompose.application.ui.theme.Color999999
import com.swein.shjetpackcompose.application.ui.theme.ColorC57644

object CommonView {

    private const val TAG = "CommonView"

    @Composable
    fun CustomToolBar(
        modifier: Modifier = Modifier,
        startImageResource: Int = -1,
        title: String = "",
        endImageResource: Int = -1,
        onStartClick: (() -> Unit)? = null,
        onEndClick: (() -> Unit)? = null
    ) {

        Surface(
            color = ColorC57644,
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp),
            elevation = 10.dp
        ) {

            val constraints = constraints()

            ConstraintLayout(
                constraints,
                modifier = modifier.fillMaxSize()
            ) {

                if (startImageResource != -1) {
                    Image(
                        painter = painterResource(id = startImageResource),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = modifier
                            .size(40.dp)
                            .layoutId("startImage")
                            .clip(CircleShape)
                            .clickable {
                                ILog.debug(TAG, "onStartImageClick")
                                onStartClick?.let {
                                    it()
                                }
                            }
                            .padding(6.dp)
                    )
                }

                if (title != "") {
                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.layoutId("title")
                    )
                }

                if (endImageResource != -1) {
                    Image(
                        painter = painterResource(id = endImageResource),
                        contentDescription = "",
                        contentScale = ContentScale.Fit,
                        modifier = modifier
                            .size(40.dp)
                            .layoutId("endImage")
                            .clip(CircleShape)
                            .clickable {
                                ILog.debug(TAG, "onEndImageClick")
                                onEndClick?.let {
                                    it()
                                }
                            }
                            .padding(6.dp)
                    )
                }


            }
        }
    }

    private fun constraints(): ConstraintSet {
        return ConstraintSet {
            val startImage = createRefFor("startImage")
            val title = createRefFor("title")
            val endImage = createRefFor("endImage")

            constrain(startImage) {
                centerVerticallyTo(parent)
                start.linkTo(parent.start, margin = 10.dp)
            }

            constrain(title) {
                centerVerticallyTo(parent)
                start.linkTo(parent.start, margin = 60.dp)
                end.linkTo(parent.end, margin = 60.dp)
            }

            constrain(endImage) {
                centerVerticallyTo(parent)
                end.linkTo(parent.end, margin = 10.dp)
            }

        }
    }


    @Composable
    fun Progress(active: Boolean = true) {

        if (active) {
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { },
                color = BlackTransparent30
//                color = colorResource(id = R.color.transparent)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = ColorC57644
                    )
                }
            }
        }
    }

    @Composable
    fun AlertDialogWithTwoButton(
        modifier: Modifier = Modifier,
        dialogState: MutableState<Boolean>,
        title: String, message: String,
        confirmButtonText: String, cancelButtonText: String,
        onConfirm: () -> Unit, onCancel: () -> Unit
    ) {

        if (dialogState.value) {

            AlertDialog(
                onDismissRequest = {
                    dialogState.value = false
                },
                title = {
                    Text(
                        text = title,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.W700,
                        fontStyle = FontStyle.Normal
                    )
                },
                text = {
                    Text(
                        text = message,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    )
                },
                buttons = {
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                         Button(
                            modifier = Modifier.weight(1f),
                            onClick = onCancel,
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color999999)
                        ) {
                            Text(
                                text = cancelButtonText,
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }

                        Button(
                            modifier = Modifier.weight(1f).padding(start = 16.dp),
                            onClick = onConfirm,
                            colors = ButtonDefaults.buttonColors(backgroundColor = ColorC57644)
                        ) {
                            Text(
                                text = confirmButtonText,
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        }

                    }
                }
            )

        }
    }

}

@Preview(showBackground = true, name = "common view preview")
@Composable
private fun CommonViewPreview() {
//    CommonView.CustomToolBar(
//        startImageResource = R.mipmap.ti_plus,
//        title = "123",
//        endImageResource = R.mipmap.ti_plus) {
//
//    }

//    CommonView.AlertDialogWithTwoButton(
//        title = "123", message = "123123123",
//        confirmButtonText = "confirm", cancelButtonText = "cancel",
//        onConfirm = {
//
//        }, onCancel = {
//
//        }
//    )
}