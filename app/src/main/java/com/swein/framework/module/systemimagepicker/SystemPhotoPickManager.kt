package com.swein.framework.module.systemimagepicker

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.swein.framework.module.permission.PermissionManager
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.BuildConfig
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

    // handler for multiple picture selection
    // need compress in thread and back to UI thread for UI updating
    private val handler = Handler(Looper.getMainLooper())

    private val takePicture: ActivityResultLauncher<Uri>
    private val selectPicture: ActivityResultLauncher<String>
    private val selectMultiplePicture: ActivityResultLauncher<String>
    private val permissionManager: PermissionManager = PermissionManager(componentActivity)

    init {
        takePicture = registerTakePicture()
        selectPicture = registerSelectPicture()
        selectMultiplePicture = registerSelectMultiplePicture()
    }

    private var selectedDelegate: ((uri: Uri) -> Unit)? = null
    private var selectedMultipleDelegate: ((uriList: List<Uri>) -> Unit)? = null
    private var selectedPathDelegate: ((imagePath: String) -> Unit)? = null
    private var selectedMultiplePathDelegate: ((imagePathList: List<String>) -> Unit)? = null

    private var takePathDelegate: ((imagePath: String) -> Unit)? = null
    private var takeUriDelegate: ((uri: Uri) -> Unit)? = null
    private var takeBitmapDelegate: ((bitmap: Bitmap) -> Unit)? = null

    private var tempImageFilePath = ""
    private var tempImageUri: Uri? = null

    private var shouldCompress = false

    private fun registerTakePicture(): ActivityResultLauncher<Uri> {

        return componentActivity.registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->

            if (success) {

                takeUriDelegate?.let { takeUriDelegate ->
                    takeUriDelegate(tempImageUri!!)
                    return@registerForActivityResult
                }

                takePathDelegate?.let { takePathDelegate ->

                    if (shouldCompress) {
                        compressImage(tempImageFilePath)
                    }

                    takePathDelegate(tempImageFilePath)
                    return@registerForActivityResult
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

                uriToFile(componentActivity, uri, "select_image")?.let { file ->

                    if (shouldCompress) {
                        compressImage(file.absolutePath)
                    }

                    it(file.absolutePath)
                }
            }

            selectedDelegate?.let {
                it(uri)
            }
        }
    }

    private fun registerSelectMultiplePicture(): ActivityResultLauncher<String> {

        return componentActivity.registerForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList ->

            if (uriList == null) {
                return@registerForActivityResult
            }

            if (uriList.isEmpty()) {
                return@registerForActivityResult
            }

            selectedMultiplePathDelegate?.let {

                Thread {

                    val imageFileList = mutableListOf<String>()

                    var index = 0
                    for (uri in uriList) {
                        ILog.debug("???", "index $index")
                        uriToFile(componentActivity, uri, "select_image_$index")?.let { file ->

                            if (shouldCompress) {
                                compressImage(file.absolutePath)
                            }

                            imageFileList.add(file.absolutePath)
                            index++
                        }

                    }

                    handler.post {
                        it(imageFileList)
                    }

                }.start()

            }

            selectedMultipleDelegate?.let {
                it(uriList)
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

    fun selectPicture(selectedDelegate: (uri: Uri) -> Unit) {

        this.selectedDelegate = selectedDelegate
        selectPicture.launch("image/*")

    }

    fun selectMultiplePicture(selectedMultipleDelegate: (uriList: List<Uri>) -> Unit) {

        this.selectedMultipleDelegate = selectedMultipleDelegate
        selectMultiplePicture.launch("image/*")
    }

    fun selectPathPicture(shouldCompress: Boolean = false, selectedPathDelegate: (imagePath: String) -> Unit) {
        this.shouldCompress = shouldCompress
        this.selectedPathDelegate = selectedPathDelegate
        selectPicture.launch("image/*")
    }

    fun selectMultiplePathPicture(shouldCompress: Boolean = false, selectedMultiplePathDelegate: (imagePathList: List<String>) -> Unit) {
        this.shouldCompress = shouldCompress
        this.selectedMultiplePathDelegate = selectedMultiplePathDelegate
        selectMultiplePicture.launch("image/*")
    }


    fun takePictureWithFilePath(shouldCompress: Boolean = false, takePathDelegate: (imagePath: String) -> Unit) {

        this.shouldCompress = shouldCompress
        this.takePathDelegate = takePathDelegate

        tempImageUri = FileProvider.getUriForFile(
            componentActivity, BuildConfig.APPLICATION_ID + ".provider",
            createImageFile().also {
                tempImageFilePath = it.absolutePath
            }
        )

        takePicture.launch(tempImageUri)

    }

    fun takePictureWithUri(takeUriDelegate: (uri: Uri) -> Unit) {

        this.takeUriDelegate = takeUriDelegate

        tempImageUri = FileProvider.getUriForFile(
            componentActivity, BuildConfig.APPLICATION_ID + ".provider",
            createImageFile().also {
                tempImageFilePath = it.absolutePath
            }
        )

        takePicture.launch(tempImageUri)

    }

    fun takePictureWithBitmap(takeBitmapDelegate: ((bitmap: Bitmap) -> Unit)) {

        this.takeBitmapDelegate = takeBitmapDelegate

        tempImageUri = FileProvider.getUriForFile(
            componentActivity, BuildConfig.APPLICATION_ID + ".provider",
            createImageFile().also {
                tempImageFilePath = it.absolutePath
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

    private fun createImageFile(imageName: String = "temp_image"): File {
        // Create an image file name
        val storageDir = componentActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            imageName,
            ".jpg",
            storageDir
        )
    }

    private fun uriToFile(context: Context, uri: Uri, fileName: String): File? {

        context.contentResolver.openInputStream(uri)?.let { inputStream ->

            val tempFile = createImageFile(fileName)
            val fileOutputStream = FileOutputStream(tempFile)

            inputStream.copyTo(fileOutputStream)
            inputStream.close()
            fileOutputStream.close()

            return tempFile
        }

        return null
    }
}