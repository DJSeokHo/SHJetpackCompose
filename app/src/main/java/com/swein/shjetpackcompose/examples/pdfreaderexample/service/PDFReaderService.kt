package com.swein.shjetpackcompose.examples.pdfreaderexample.service

import android.content.Context
import android.os.Environment
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

object PDFReaderService {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

    private data class PDFReaderServiceResponse(val call: Call, val response: Response)

    private fun requestGet(url: String): PDFReaderServiceResponse {

        val builder = Request.Builder()

        val request = builder.get().url(url).build()

        val call = okHttpClient.newCall(request)

        val result = call.execute()

        return PDFReaderServiceResponse(call, result)
    }

    private fun cancelCall(call: Call) {
        if (!call.isCanceled()) {
            call.cancel()
        }
    }

    suspend fun pdf(
        context: Context, url: String, onProgress: (progress: Int) -> Unit
    ): File? = withContext(Dispatchers.IO) {

        val coroutineResponse = requestGet(url)

        val responseFile = getFileResponse(context, coroutineResponse.response, onProgress)
        cancelCall(coroutineResponse.call)

        return@withContext responseFile
    }

    private fun getFileResponse(
        context: Context, response: Response, onProgress: (progress: Int) -> Unit
    ): File? {
        Log.d("???", "getFileResponse")

        val responseBody = response.body

        responseBody?.let {

            return try {

                val pdfFilePath = createPdfTempFile(context)

                val inputStream = responseBody.byteStream()
                val outputStream = FileOutputStream(pdfFilePath)
                val target = it.contentLength()

                // copy stream with progress
                copyStream(inputStream, outputStream) { totalBytesCopied ->

                    Log.d("???", "$totalBytesCopied/$target")

                    onProgress(((totalBytesCopied.toFloat() / target.toFloat()) * 100).toInt())
                }

                outputStream.flush()
                outputStream.close()
                inputStream.close()

                pdfFilePath
            }
            catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } ?: run {
            return null
        }
    }

    private fun copyStream(inputStream: InputStream, outputStream: OutputStream,
                           onCopy: (totalBytesCopied: Long) -> Unit) {

        var bytesCopied = 0L

        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var bytes = inputStream.read(buffer)

        while (bytes >= 0) {
            outputStream.write(buffer, 0, bytes)
            bytesCopied += bytes

            onCopy(bytesCopied)

            bytes = inputStream.read(buffer)
        }
    }

    private fun createPdfTempFile(context: Context): File {

        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        return File.createTempFile("temp_pdf", ".pdf", storageDir)
    }
}