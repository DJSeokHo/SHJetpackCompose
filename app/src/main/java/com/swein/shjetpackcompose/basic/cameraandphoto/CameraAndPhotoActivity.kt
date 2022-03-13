package com.swein.shjetpackcompose.basic.cameraandphoto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.swein.framework.module.systemimagepicker.SystemPhotoPickManager
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.application.ui.theme.Color111111
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CameraAndPhotoActivity : ComponentActivity() {

    private val systemPhotoPickManager = SystemPhotoPickManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            ContentView()

        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun ContentView() {

        val contentImage = remember {
            mutableStateOf("")
        }

        val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()

        Box(
            modifier = Modifier.fillMaxSize().background(Color.DarkGray)
        ) {

            PhotoView(
                contentImage = contentImage.value,
                scope = scope,
                state = state
            )

            BottomActionSheet(
                state = state,
                scope = scope,
                onTakePhoto = {
                    takePhoto {
                        contentImage.value = it
                    }
                },
                onSelectImage = {
                    selectImage {
                        contentImage.value = it
                    }
                }
            )

        }
    }


    @OptIn(ExperimentalCoilApi::class, ExperimentalMaterialApi::class)
    @Composable
    fun PhotoView(modifier: Modifier = Modifier, contentImage: String, scope: CoroutineScope, state: ModalBottomSheetState) {

        Image(
            painter = if (contentImage == "") {
                painterResource(id = R.mipmap.ti_image)
            }
            else {
                rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .crossfade(true)
                        .data(contentImage)
                        .build(),
                    filterQuality = FilterQuality.High
                )
            },
            contentDescription = "",
            modifier = modifier
                .fillMaxWidth()
                .padding(20.dp)
                .defaultMinSize(minHeight = 100.dp)
                .clickable {
                    scope.launch {
                        state.show()
                    }
                }
                .background(color = Color.White),
            contentScale = if (contentImage == "") {
                ContentScale.Inside
            }
            else {
                ContentScale.FillWidth
            }
        )
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun BottomActionSheet(
        modifier: Modifier = Modifier, state: ModalBottomSheetState, scope: CoroutineScope,
        onTakePhoto: () -> Unit, onSelectImage: () -> Unit
    ) {

        ModalBottomSheetLayout(
            sheetState = state,
            sheetContent = {
                Column{

                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clickable {

                                scope.launch {
                                    state.hide()
                                }

                                onTakePhoto()
                            }
                    ) {

                        Image(
                            painter = painterResource(id = R.mipmap.ti_camera),
                            contentDescription = "",
                            modifier = modifier
                                .size(40.dp)
                                .align(Alignment.CenterVertically),
                            contentScale = ContentScale.Inside
                        )
                        Spacer(modifier = modifier.width(10.dp))
                        Text(
                            text = "camera", color = Color111111,
                            fontSize = 15.sp, modifier = modifier.align(Alignment.CenterVertically)
                        )
                    }

                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clickable {

                                scope.launch {
                                    state.hide()
                                }

                                onSelectImage()
                            }
                    ) {

                        Image(
                            painter = painterResource(id = R.mipmap.ti_gallery),
                            contentDescription = "",
                            modifier = modifier
                                .size(40.dp)
                                .align(Alignment.CenterVertically),
                            contentScale = ContentScale.Inside
                        )
                        Spacer(modifier = modifier.width(10.dp))
                        Text(
                            text = "gallery", color = Color111111,
                            fontSize = 15.sp, modifier = modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        ) {

        }

        BackHandler(
            enabled = (state.currentValue == ModalBottomSheetValue.HalfExpanded
                    || state.currentValue == ModalBottomSheetValue.Expanded),
            onBack = {
                scope.launch{
                    state.hide()
                }
            }
        )
    }

    private fun takePhoto(onResult: (result: String) -> Unit) {

        systemPhotoPickManager.requestPermission {

            it.takePictureWithFilePath(true) { imagePath ->

                onResult(imagePath)
            }

        }
    }

    private fun selectImage(onResult: (result: String) -> Unit) {
        systemPhotoPickManager.requestPermission {

            it.selectPathPicture(true) { imagePath ->

                onResult(imagePath)
            }

        }
    }
}