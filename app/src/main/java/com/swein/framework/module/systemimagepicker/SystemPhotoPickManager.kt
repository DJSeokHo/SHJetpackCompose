package com.swein.framework.module.systemimagepicker

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.swein.easypermissionmanager.EasyPermissionManager
import com.swein.framework.utility.debug.ILog
import java.io.File
import java.io.FileOutputStream
import java.util.*

/**
StartActivityForResult()
RequestMultiplePermissions()
RequestPermission()
TakePicturePreview()
TakePicture()
TakeVideo()
PickContact()
CreateDocument()
OpenDocumentTree()
OpenMultipleDocuments()
OpenDocument()
GetMultipleContents()
GetContent()
 */
class SystemPhotoPickManager(private val componentActivity: ComponentActivity) {

    companion object {
        private const val TAG = "SystemPhotoPickManager"
    }

    private val takePicture: ActivityResultLauncher<Uri>
    private val selectPicture: ActivityResultLauncher<String>
    private val permissionManager: EasyPermissionManager = EasyPermissionManager(componentActivity)

    init {
        takePicture = registerTakePicture()
        selectPicture = registerSelectPicture()
    }

    private var selectedUriDelegate: ((uri: Uri) -> Unit)? = null
    private var selectedPathDelegate: ((imagePath: String) -> Unit)? = null

    private var takePathDelegate: ((imagePath: String) -> Unit)? = null
    private var takeUriDelegate: ((uri: Uri) -> Unit)? = null
    private var takeBitmapDelegate: ((bitmap: Bitmap) -> Unit)? = null

    private var tempImageFilePath = ""
    private var tempImageUri: Uri? = null
    private var tempImageName = ""

    private var shouldCompress = false

    private fun registerTakePicture(): ActivityResultLauncher<Uri> {

        return componentActivity.registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->

            if (success) {

                takeUriDelegate?.let { takeUriDelegate ->
                    takeUriDelegate(tempImageUri!!)
                }

                takePathDelegate?.let { takePathDelegate ->

                    if (shouldCompress) {
                        compressImage(tempImageFilePath)
                    }

                    takePathDelegate(tempImageFilePath)
                }

                takeBitmapDelegate?.let { takeBitmapDelegate ->

                    val exif = ExifInterface(tempImageFilePath)
                    val exifOrientation: Int = exif.getAttributeInt(
                        ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
                    )
                    val exifDegree: Int = exifOrientationToDegrees(exifOrientation)

                    takeBitmapDelegate(rotateImage(BitmapFactory.decodeStream(componentActivity.contentResolver.openInputStream(tempImageUri!!)), exifDegree.toFloat()))
                }

            }

        }
    }

    private fun registerSelectPicture(): ActivityResultLauncher<String> {

        return componentActivity.registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->

            if (uri == null) {
                return@registerForActivityResult
            }

            selectedPathDelegate?.let {
                uriToFile(componentActivity, uri)?.let { file ->

                    if (shouldCompress) {
                        compressImage(file.absolutePath)
                    }

                    it(file.absolutePath)
                }
            }

            selectedUriDelegate?.let {
                it(uri)
            }
        }
    }

    fun requestPermission(permissionDialogTitle: String = "permission",
                          permissionDialogMessage: String = "permissions are necessary",
                          permissionDialogPositiveButtonTitle: String =  "setting",
                          runnable: (SystemPhotoPickManager) -> Unit) {

        permissionManager.requestPermission(
            permissionDialogTitle,
            permissionDialogMessage,
            permissionDialogPositiveButtonTitle,
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        ) {
            runnable(this)
        }
    }

    fun selectPicture(
        shouldCompress: Boolean = false,
        selectedUriDelegate: (uri: Uri) -> Unit,
        selectedPathDelegate: (imagePath: String) -> Unit,
        imageName: String = "temp_image") {

        this.shouldCompress = shouldCompress
        this.selectedUriDelegate = selectedUriDelegate
        this.selectedPathDelegate = selectedPathDelegate

        this.tempImageName = imageName

        selectPicture.launch("image/*")

    }

    fun takePicture(
        shouldCompress: Boolean = false,
        takeUriDelegate: (uri: Uri) -> Unit,
        takePathDelegate: (imagePath: String) -> Unit,
        takeBitmapDelegate: (bitmap: Bitmap) -> Unit,
        imageName: String = "temp_image"
    ) {

        this.shouldCompress = shouldCompress
        this.takeUriDelegate = takeUriDelegate
        this.takePathDelegate = takePathDelegate
        this.takeBitmapDelegate = takeBitmapDelegate

        this.tempImageName = imageName

        tempImageUri = FileProvider.getUriForFile(
            componentActivity, "com.swein.shjetpackcompose.provider",
            createImageFile().also { file ->
                tempImageFilePath = file.absolutePath
            }
        )

        takePicture.launch(tempImageUri)
    }

    private fun compressImage(filePath: String, targetMB: Double = 1.0) {
        var image: Bitmap = BitmapFactory.decodeFile(filePath)

        val exif = ExifInterface(filePath)
        val exifOrientation: Int = exif.getAttributeInt(
            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL
        )
        val exifDegree: Int = exifOrientationToDegrees(exifOrientation)

        image = rotateImage(image, exifDegree.toFloat())

        try {

            val file = File(filePath)
            val length = file.length()

            val fileSizeInKB = (length / 1024).toString().toDouble()
            val fileSizeInMB = (fileSizeInKB / 1024).toString().toDouble()

            var quality = 100
            if(fileSizeInMB > targetMB) {
                quality = ((targetMB / fileSizeInMB) * 100).toInt()
            }

            val fileOutputStream = FileOutputStream(filePath)
            image.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream)
            fileOutputStream.close()
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

    private fun exifOrientationToDegrees(exifOrientation: Int): Int {
        return when (exifOrientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                90
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                180
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                270
            }
            else -> 0
        }
    }

    private fun createImageFile(): File {
        // Create an image file name
        val storageDir = componentActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            tempImageName,
            ".jpg",
            storageDir
        )
    }

    private fun uriToFile(context: Context, uri: Uri): File? {

        context.contentResolver.openInputStream(uri)?.let { inputStream ->

            val tempFile = createImageFile()
            val fileOutputStream = FileOutputStream(tempFile)

            inputStream.copyTo(fileOutputStream)
            inputStream.close()
            fileOutputStream.close()

            return tempFile
        }

        return null
    }
}