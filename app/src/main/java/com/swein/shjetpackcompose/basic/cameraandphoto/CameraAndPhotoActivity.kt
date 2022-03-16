package com.swein.shjetpackcompose.basic.cameraandphoto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.swein.shjetpackcompose.application.ui.theme.ColorFFFF781C
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CameraAndPhotoActivity : ComponentActivity() {

    private val systemPhotoPickManager = SystemPhotoPickManager(this)

    private val viewModel: CameraAndPhotoViewModel by viewModels()

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

//        val contentImage = remember {
//            mutableStateOf("")
//        }

        val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()

        Box(
            modifier = Modifier.fillMaxSize().background(Color.DarkGray)
        ) {

            BottomActionSheet(
                state = state,
                scope = scope,
                onTakeImage = {

                    if (it) {
                        viewModel.takeImage(systemPhotoPickManager, true)
//                        takePhoto {
//                            contentImage.value = it
//                        }
                    }
                    else {
                        viewModel.takeImage(systemPhotoPickManager, false)
//                        selectPhoto {
//                            contentImage.value = it
//                        }
                    }

                }
            ) {

                PhotoView(
//                    imagePath = contentImage.value,
                imagePath = viewModel.imagePath.value,
                    scope = scope,
                    state = state
                )
            }

        }
    }


    @OptIn(ExperimentalCoilApi::class, ExperimentalMaterialApi::class)
    @Composable
    fun PhotoView(modifier: Modifier = Modifier, imagePath: String, scope: CoroutineScope, state: ModalBottomSheetState) {

        Image(
            painter = if (imagePath == "") {
                painterResource(id = R.mipmap.ti_image)
            }
            else {
                rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .crossfade(true)
                        .data(imagePath)
                        .build(),
                    filterQuality = FilterQuality.High
                )
            },
            contentDescription = "",
            modifier = modifier
                .fillMaxWidth()
                .padding(40.dp)
                .defaultMinSize(minHeight = 100.dp)
                .clickable {
                    scope.launch {
                        state.show()
                    }
                }
                .background(color = Color.Transparent)
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, ColorFFFF781C, RoundedCornerShape(16.dp)),
            contentScale = if (imagePath == "") {
                ContentScale.Inside
            }
            else {
                ContentScale.FillWidth
            }
        )
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun BottomActionSheet(state: ModalBottomSheetState, scope: CoroutineScope,
        onTakeImage: (isCamera: Boolean) -> Unit, modalBottomSheetLayoutScope: @Composable () -> Unit
    ) {

        ModalBottomSheetLayout(
            sheetState = state,
            sheetContent = {
                Column{

                    BottomActionItem(
                        title = "camera",
                        resource = R.mipmap.ti_camera,
                        isCamera = true
                    ) { isCamera ->

                        scope.launch {
                            state.hide()
                        }

                        onTakeImage(isCamera)
                    }

                    BottomActionItem(
                        title = "gallery",
                        resource = R.mipmap.ti_gallery,
                        isCamera = false
                    ) { isCamera ->

                        scope.launch {
                            state.hide()
                        }

                        onTakeImage(isCamera)
                    }
                }
            }
        ) {
            modalBottomSheetLayoutScope()
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

    @Composable
    private fun BottomActionItem(modifier: Modifier = Modifier, title: String, resource: Int, isCamera: Boolean, onTakeImage: (isCamera: Boolean) -> Unit) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
                .clickable {

                    onTakeImage(isCamera)
                }
        ) {

            Image(
                painter = painterResource(id = resource),
                contentDescription = "",
                modifier = modifier
                    .size(40.dp)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Inside
            )
            Spacer(modifier = modifier.width(10.dp))
            Text(
                text = title, color = Color111111,
                fontSize = 15.sp, modifier = modifier.align(Alignment.CenterVertically)
            )
        }
    }


//    private fun takePhoto(onResult: (result: String) -> Unit) {
//
//        systemPhotoPickManager.requestPermission {
//
//            it.takePictureWithFilePath(true) { imagePath ->
//
//                onResult(imagePath)
//            }
//
//        }
//    }
//
//    private fun selectPhoto(onResult: (result: String) -> Unit) {
//        systemPhotoPickManager.requestPermission {
//
//            it.selectPathPicture(true) { imagePath ->
//
//                onResult(imagePath)
//            }
//
//        }
//    }
}