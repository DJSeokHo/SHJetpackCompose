package com.swein.shjetpackcompose.examples.newsystemphotopicker

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

class NewPhotoPickWrapper(
    activity: ComponentActivity,
    multipleMaximum: Int
) {

    private val activityResultLauncherForSingle: ActivityResultLauncher<PickVisualMediaRequest>
    private val activityResultLauncherForMultiple: ActivityResultLauncher<PickVisualMediaRequest>

    private var onSingle: ((uri: Uri) -> Unit)? = null
    private var onMultiple: ((uris: List<Uri>) -> Unit)? = null
    private var onEmpty: (() -> Unit)? = null

    init {
        activityResultLauncherForSingle = activity.registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                onSingle?.let {
                    it(uri)
                }
            }
            else {
                onEmpty?.let {
                    it()
                }
            }
        }

        activityResultLauncherForMultiple = activity.registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(multipleMaximum)) { uris ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uris != null && uris.isNotEmpty()) {
                onMultiple?.let {
                    it(uris)
                }
            }
            else {
                onEmpty?.let {
                    it()
                }
            }
        }
    }

    fun singleImage(onSingle: (uri: Uri) -> Unit, onEmpty: () -> Unit) {
        if (ActivityResultContracts.PickVisualMedia.isPhotoPickerAvailable()) {
            this.onSingle = onSingle
            this.onEmpty = onEmpty
            activityResultLauncherForSingle.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    fun multipleImages(onMultiple: (uris: List<Uri>) -> Unit, onEmpty: () -> Unit) {
        if (ActivityResultContracts.PickVisualMedia.isPhotoPickerAvailable()) {
            this.onMultiple = onMultiple
            this.onEmpty = onEmpty
            activityResultLauncherForMultiple.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }
}