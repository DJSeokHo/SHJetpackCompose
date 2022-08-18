package com.swein.shjetpackcompose.examples.pdfreaderexample.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swein.shjetpackcompose.examples.pdfreaderexample.service.PDFReaderService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PDFReaderViewModelState {

    val error = mutableStateOf("")
    fun toggleError(errorMessage: String) {
        error.value = errorMessage
    }

    val loading = mutableStateOf(false)
    fun toggleLoading(isLoading: Boolean) {
        loading.value = isLoading
    }

    val progress = mutableStateOf("")
    fun toggleProgress(value: String) {
        progress.value = value
    }

    val pdfFile = mutableStateOf<File?>(null)
    fun toggleFile(file: File?) {
        pdfFile.value = file
    }
}


class PDFReaderViewModel: ViewModel() {

    val state = PDFReaderViewModelState()

    private val totalBitmapList = mutableListOf<Bitmap>()
    val bitmapList = mutableStateListOf<Bitmap>()

    fun pdf(context: Context, url: String, onSuccess: () -> Unit) = viewModelScope.launch {

        state.toggleProgress("")
        state.toggleLoading(true)
        state.toggleError("")

        try {

            coroutineScope {

                val pdfService = async {

                    PDFReaderService.pdf(context, url) {

//                        Log.d("???", "progress $it%")
                        state.toggleProgress("$it%")
                    }
                }

                val pdfFile = pdfService.await()

                state.toggleProgress("")
                state.toggleLoading(false)
                state.toggleFile(pdfFile)

                onSuccess()
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            state.toggleLoading(false)
            state.toggleError(e.message.toString())
        }

    }

    fun bitmaps(file: File, screenWidth: Int, onSuccess: () -> Unit) = viewModelScope.launch {

        state.toggleLoading(true)
        state.toggleError("")

        try {

            coroutineScope {

                val bitmaps = async(Dispatchers.IO) {

                    getBitmapsFromPdfRenderer(file, screenWidth)
                }

                val result = bitmaps.await()

                state.toggleLoading(false)

                totalBitmapList.clear()
                bitmapList.clear()

                totalBitmapList.addAll(result)

                onSuccess()
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            state.toggleLoading(false)
            state.toggleError(e.message.toString())
        }
    }

    /*
        if a pdf file has so many pages,
        we need pagination.
        download one time and fetch pages with load more
     */
    fun fetchData(offset: Int, limit: Int) {

        val list = mutableListOf<Bitmap>()

        for (i in offset until offset + limit) {

            if (i > totalBitmapList.size - 1) {
                break
            }

            list.add(totalBitmapList[i])
        }

        bitmapList.addAll(list)
    }

    private suspend fun getBitmapsFromPdfRenderer(
        file: File, screenWidth: Int
    ): List<Bitmap> {

        return suspendCoroutine { continuation ->

            Thread {

                try {

                    val parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)

                    val pdfRenderer = PdfRenderer(parcelFileDescriptor)

                    val list = mutableListOf<Bitmap>()

                    for (i in 0 until pdfRenderer.pageCount) {

                        val page = pdfRenderer.openPage(i)

                        val scale = screenWidth.toFloat() / page.width

                        Bitmap.createBitmap(
                            (page.width * scale).toInt(),
                            (page.height * scale).toInt(),
                            Bitmap.Config.ARGB_8888
                        ).also {

                            page.render(it, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

                            page.close()

                            list.add(it)
                        }
                    }
                    // end of for

                    parcelFileDescriptor.close()
                    pdfRenderer.close()

                    continuation.resume(list)

                }
                catch (e: Exception) {
                    continuation.resumeWithException(e)
                }
            }.start()

        }


    }

}