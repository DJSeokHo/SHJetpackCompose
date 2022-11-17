package com.swein.shjetpackcompose.examples.newsystemphotopicker

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NewSystemPhotoPickerActivity : ComponentActivity() {

    // why here ???
    // because "LifecycleOwners must call register before they are STARTED", said by android studio, not me!!
    private val wrapper = NewPhotoPickWrapper(this, 2)

    private val imageUris = mutableStateListOf<Uri>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {
            ContentView(
                imageUris = imageUris,
                onSingle = {
                    wrapper.singleImage(
                        onSingle = {
                            imageUris.clear()
                            imageUris.add(it)
                        },
                        onEmpty = {
                            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                onMultiple = {
                    wrapper.multipleImages(
                        onMultiple = {
                            imageUris.clear()
                            imageUris.addAll(it)
                        },
                        onEmpty = {
                            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ContentView(
    imageUris: MutableList<Uri>,
    onSingle: () -> Unit,
    onMultiple: () -> Unit
) {

    val context = LocalContext.current
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetBackgroundColor = Color.White,
        sheetElevation = 5.dp,
        sheetShape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp),
        sheetState = state,
        sheetContent = {

            BottomActionSheetView(
                scope = scope,
                state = state,
                onSingle = onSingle,
                onMultiple = onMultiple
            )

        }
    ) {

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.LightGray
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 70.dp, top = 16.dp)
                ) {

                    for (imageUri in imageUris) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(context = context)
                                    .crossfade(true)
                                    .data(imageUri)
                                    .size(Size.ORIGINAL)
                                    .build(),
                                filterQuality = FilterQuality.High
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .wrapContentHeight()
                                .weight(1f)
                                .clip(shape = RoundedCornerShape(6.dp)),
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray, contentColor = Color.White),
                    onClick = {
                        scope.launch {
                            state.animateTo(ModalBottomSheetValue.Expanded, tween(500))
                        }
                    }
                ) {
                    Text(
                        text = "Pick",
                        fontSize = 15.sp
                    )
                }
            }
        }

        BackHandler(
            enabled = (state.currentValue == ModalBottomSheetValue.HalfExpanded
                    || state.currentValue == ModalBottomSheetValue.Expanded),
            onBack = {
                scope.launch{
                    state.animateTo(ModalBottomSheetValue.Hidden, tween(300))
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun BottomActionSheetView(
    scope: CoroutineScope,
    state: ModalBottomSheetState,
    onSingle: () -> Unit,
    onMultiple: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {

        Button(
            modifier = Modifier
                .wrapContentSize(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray, contentColor = Color.White),
            onClick = {

                scope.launch{
                    state.animateTo(ModalBottomSheetValue.Hidden, tween(300))
                }

                onSingle()
            }
        ) {
            Text(
                text = "Single Photo",
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.padding(horizontal = 26.dp))

        Button(
            modifier = Modifier
                .wrapContentSize(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray, contentColor = Color.White),
            onClick = {

                scope.launch{
                    state.animateTo(ModalBottomSheetValue.Hidden, tween(300))
                }

                onMultiple()
            }
        ) {
            Text(
                text = "Multiple Photo",
                fontSize = 15.sp
            )
        }
    }
}