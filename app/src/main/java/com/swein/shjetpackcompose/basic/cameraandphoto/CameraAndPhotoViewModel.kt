package com.swein.shjetpackcompose.basic.cameraandphoto

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swein.framework.module.systemimagepicker.SystemPhotoPickManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class CameraAndPhotoViewModel: ViewModel() {

    companion object {
        private const val TAG = "CameraAndPhotoViewModel"
    }

    var imagePath = mutableStateOf("")

    fun takeImage(systemPhotoPickManager: SystemPhotoPickManager, isCamera: Boolean) = viewModelScope.launch {

        try {
            coroutineScope {

                val imagePathResult = async(Dispatchers.IO) {

                    if (isCamera) {
                        camera(systemPhotoPickManager)
                    }
                    else {
                        album(systemPhotoPickManager)
                    }

                }

                imagePath.value = imagePathResult.await()

            }
        }
        catch (e: Exception) {
            Log.d(TAG, e.toString())
        }
    }


    private suspend fun camera(systemPhotoPickManager: SystemPhotoPickManager): String {

        return suspendCoroutine { continuation ->

            systemPhotoPickManager.requestPermission {

                it.takePictureWithFilePath(true) { imagePath ->

                    continuation.resume(imagePath)
                }
            }
        }
    }

    private suspend fun album(systemPhotoPickManager: SystemPhotoPickManager): String {

        return suspendCoroutine { continuation ->

            systemPhotoPickManager.requestPermission {

                it.selectPathPicture(true) { imagePath ->

                    continuation.resume(imagePath)
                }
            }
        }
    }

}