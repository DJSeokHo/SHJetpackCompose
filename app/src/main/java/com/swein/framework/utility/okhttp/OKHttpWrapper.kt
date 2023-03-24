package com.swein.framework.utility.okhttp

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
import com.swein.framework.utility.debug.ILog
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import java.io.IOException


/**
 *
 * After Android 9.0
 * OKHttp can not access http
 *
 * so add this in AndroidManifest.xml between the <application></application>
 *
 * <uses-library android:name="org.apache.http.legacy" android:required="false"/>
 *
 * and add android:usesCleartextTraffic="true" in the <application>
 */

private var okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

data class FormDataFile(
    val filePath: String,
    val fileName: String
)

interface OKHttpWrapperDelegate {
    fun onFailure(call: Call, e: IOException)
    fun onResponse(call: Call, response: Response)
}

data class CoroutineResponse(val call: Call, val response: Response)

object OKHttpWrapper {

    const val TAG = "OKHttpWrapper"

    fun cancelCall(call: Call) {
        if (!call.isCanceled()) {
            call.cancel()
        }
    }

    fun getStringResponse(response: Response, defaultResponse: String = ""): String {
        ILog.debug(TAG, "onResponse: $response")
        val responseBody = response.body

        responseBody?.let {
            return try {
                it.string()
            } catch (e: Exception) {
                "exception ${e.message.toString()}"
            }

        } ?: run {
            return defaultResponse
        }
    }

    /**
     * for callback
     */
    fun requestGet(
        url: String,
        header: MutableMap<String, String>? = null,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        val builder = Request.Builder()

        // if header
        header?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }

        val request = builder.get().url(url).build()
        val call = okHttpClient.newCall(request)

        // auto  thread
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpWrapperDelegate.onFailure(call, e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                okHttpWrapperDelegate.onResponse(call, response)
            }
        })
    }

    /**
     * for coroutine
     */
    fun requestGet(
        url: String,
        header: MutableMap<String, String>? = null
    ): CoroutineResponse {

        val builder = Request.Builder()

        // if header
        header?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }

        val request = builder.get().url(url).build()
        val call = okHttpClient.newCall(request)

        val result = call.execute()
        return CoroutineResponse(call, result)
    }

    /**
     * for callback
     */
    fun requestPost(
        url: String,
        header: MutableMap<String, String>? = null,
        formData: MutableMap<String, String>? = null,
        fileList: MutableList<FormDataFile>? = null,
        fileKey: String = "",
        jsonObject: JSONObject? = null,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        val builder = Request.Builder()

        // if header
        header?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }

        val requestBody: RequestBody
        // if form data
        if (formData != null || fileKey != "") {

            val multipartBodyBuilder: MultipartBody.Builder = MultipartBody.Builder()
            multipartBodyBuilder.setType(MultipartBody.FORM)

            formData?.let {
                ILog.debug(TAG, "add form data")
                for ((key, value) in formData) {
                    ILog.debug(TAG, "$key $value")
                    multipartBodyBuilder.addFormDataPart(key, value)
                }
            }

            if (fileList != null) {
                ILog.debug(TAG, "add files")
                var file: File
                for (i in fileList.indices) {
                    ILog.debug(TAG, fileList[i].fileName)
                    val mediaType: MediaType = if (fileList[i].fileName.endsWith("png")) {
                        "image/png".toMediaType()
                    } else {
                        "image/jpeg".toMediaType()
                    }
                    file = File(fileList[i].filePath)
                    multipartBodyBuilder.addFormDataPart(fileKey, fileList[i].fileName, file.asRequestBody(mediaType))
                }
            }

            requestBody = multipartBodyBuilder.build()
        }
        else {

            val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
            requestBody = jsonObject?.toString()?.toRequestBody(mediaType) ?: "".toRequestBody(mediaType)
        }

        val request = builder.post(requestBody).url(url).build()
        val call = okHttpClient.newCall(request)

        // auto  thread
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpWrapperDelegate.onFailure(call, e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                okHttpWrapperDelegate.onResponse(call, response)
            }
        })
    }

    /**
     * for coroutine
     */
    fun requestPost(
        url: String,
        header: MutableMap<String, String>? = null,
        formData: MutableMap<String, String>? = null,
        fileList: MutableList<FormDataFile>? = null,
        fileKey: String = "",
        jsonObject: JSONObject? = null
    ): CoroutineResponse {

        val builder = Request.Builder()

        // if header
        header?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }

        val requestBody: RequestBody
        // if form data
        if (formData != null || fileKey != "") {

            val multipartBodyBuilder: MultipartBody.Builder = MultipartBody.Builder()
            multipartBodyBuilder.setType(MultipartBody.FORM)

            formData?.let {
                ILog.debug(TAG, "add form data")
                for ((key, value) in formData) {
                    ILog.debug(TAG, "$key $value")
                    multipartBodyBuilder.addFormDataPart(key, value)
                }
            }

            if (fileList != null) {
                ILog.debug(TAG, "add files")
                var file: File
                for (i in fileList.indices) {
                    ILog.debug(TAG, fileList[i].fileName)
                    val mediaType: MediaType = if (fileList[i].fileName.endsWith("png")) {
                        "image/png".toMediaType()
                    } else {
                        "image/jpeg".toMediaType()
                    }
                    file = File(fileList[i].filePath)
                    multipartBodyBuilder.addFormDataPart(fileKey, fileList[i].fileName, file.asRequestBody(mediaType))
                }
            }

            requestBody = multipartBodyBuilder.build()
        }
        else {

            val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
            requestBody = jsonObject?.toString()?.toRequestBody(mediaType) ?: "".toRequestBody(mediaType)
        }

        val request = builder.post(requestBody).url(url).build()
        val call = okHttpClient.newCall(request)

        val result = call.execute()
        return CoroutineResponse(call, result)
    }

    /**
     * for coroutine
     */
    fun requestPost(
        url: String,
        header: MutableMap<String, String>? = null,
        formData: MutableMap<String, String>? = null,
        file: File? = null,
        mediaType: String = "",
        fileKey: String = ""
    ): CoroutineResponse {

        val builder = Request.Builder()

        // if header
        header?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }

        val requestBody: RequestBody
        // if form data
        val multipartBodyBuilder: MultipartBody.Builder = MultipartBody.Builder()
        multipartBodyBuilder.setType(MultipartBody.FORM)

        formData?.let {
            ILog.debug(TAG, "add form data")
            for ((key, value) in formData) {
                ILog.debug(TAG, "$key $value")
                multipartBodyBuilder.addFormDataPart(key, value)
            }
        }

        if (file != null) {
            ILog.debug(TAG, "add file $mediaType")
            multipartBodyBuilder.addFormDataPart(fileKey, file.name, file.asRequestBody(mediaType.toMediaType()))
        }

        requestBody = multipartBodyBuilder.build()

        val request = builder.post(requestBody).url(url).build()
        val call = okHttpClient.newCall(request)

        val result = call.execute()
        return CoroutineResponse(call, result)
    }

    /**
     * for callback
     */
    fun requestPut(
        url: String,
        header: MutableMap<String, String>? = null,
        formData: MutableMap<String, String>? = null,
        fileList: MutableList<FormDataFile>? = null,
        fileKey: String = "",
        jsonObject: JSONObject? = null,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        val builder = Request.Builder()

        // if header
        header?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }

        val requestBody: RequestBody
        // if form data
        if (formData != null || fileKey != "") {

            val multipartBodyBuilder: MultipartBody.Builder = MultipartBody.Builder()
            multipartBodyBuilder.setType(MultipartBody.FORM)

            formData?.let {
                ILog.debug(TAG, "add form data")
                for ((key, value) in formData) {
                    ILog.debug(TAG, "$key $value")
                    multipartBodyBuilder.addFormDataPart(key, value)
                }
            }

            if (fileList != null) {
                ILog.debug(TAG, "add files")
                var file: File
                for (i in fileList.indices) {
                    ILog.debug(TAG, fileList[i].fileName)
                    val mediaType: MediaType = if (fileList[i].fileName.endsWith("png")) {
                        "image/png".toMediaType()
                    } else {
                        "image/jpeg".toMediaType()
                    }
                    file = File(fileList[i].filePath)
                    multipartBodyBuilder.addFormDataPart(fileKey, fileList[i].fileName, file.asRequestBody(mediaType))
                }
            }

            requestBody = multipartBodyBuilder.build()
        }
        else {

            val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
            requestBody = jsonObject?.toString()?.toRequestBody(mediaType) ?: "".toRequestBody(mediaType)
        }

        val request = builder.put(requestBody).url(url).build()
        val call = okHttpClient.newCall(request)

        // auto  thread
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpWrapperDelegate.onFailure(call, e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                okHttpWrapperDelegate.onResponse(call, response)
            }
        })
    }

    /**
     * for coroutine
     */
    fun requestPut(
        url: String,
        header: MutableMap<String, String>? = null,
        formData: MutableMap<String, String>? = null,
        fileList: MutableList<FormDataFile>? = null,
        fileKey: String = "",
        jsonObject: JSONObject? = null
    ): CoroutineResponse {

        val builder = Request.Builder()

        // if header
        header?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }

        val requestBody: RequestBody
        // if form data
        if (formData != null || fileKey != "") {

            val multipartBodyBuilder: MultipartBody.Builder = MultipartBody.Builder()
            multipartBodyBuilder.setType(MultipartBody.FORM)

            formData?.let {
                ILog.debug(TAG, "add form data")
                for ((key, value) in formData) {
                    ILog.debug(TAG, "$key $value")
                    multipartBodyBuilder.addFormDataPart(key, value)
                }
            }

            if (fileList != null) {
                ILog.debug(TAG, "add files")
                var file: File
                for (i in fileList.indices) {
                    ILog.debug(TAG, fileList[i].fileName)
                    val mediaType: MediaType = if (fileList[i].fileName.endsWith("png")) {
                        "image/png".toMediaType()
                    } else {
                        "image/jpeg".toMediaType()
                    }
                    file = File(fileList[i].filePath)
                    multipartBodyBuilder.addFormDataPart(fileKey, fileList[i].fileName, file.asRequestBody(mediaType))
                }
            }

            requestBody = multipartBodyBuilder.build()
        }
        else {

            val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
            requestBody = jsonObject?.toString()?.toRequestBody(mediaType) ?: "".toRequestBody(mediaType)
        }

        val request = builder.put(requestBody).url(url).build()
        val call = okHttpClient.newCall(request)

        val result = call.execute()
        return CoroutineResponse(call, result)
    }

    /**
     * for callback
     */
    fun requestDelete(
        url: String,
        header: MutableMap<String, String>? = null,
        formData: MutableMap<String, String>? = null,
        fileList: MutableList<FormDataFile>? = null,
        fileKey: String = "",
        jsonObject: JSONObject? = null,
        okHttpWrapperDelegate: OKHttpWrapperDelegate
    ) {

        val builder = Request.Builder()

        // if header
        header?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }

        val requestBody: RequestBody
        // if form data
        if (formData != null) {

            val multipartBodyBuilder: MultipartBody.Builder = MultipartBody.Builder()
            multipartBodyBuilder.setType(MultipartBody.FORM)

            formData.let {
                ILog.debug(TAG, "add form data")
                for ((key, value) in formData) {
                    ILog.debug(TAG, "$key $value")
                    multipartBodyBuilder.addFormDataPart(key, value)
                }
            }

            if (fileList != null) {
                ILog.debug(TAG, "add files")
                var file: File
                for (i in fileList.indices) {
                    ILog.debug(TAG, fileList[i].fileName)
                    val mediaType: MediaType = if (fileList[i].fileName.endsWith("png")) {
                        "image/png".toMediaType()
                    } else {
                        "image/jpeg".toMediaType()
                    }
                    file = File(fileList[i].filePath)
                    multipartBodyBuilder.addFormDataPart(fileKey, fileList[i].fileName, file.asRequestBody(mediaType))
                }
            }

            requestBody = multipartBodyBuilder.build()
        }
        else {

            val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
            requestBody = jsonObject?.toString()?.toRequestBody(mediaType) ?: "".toRequestBody(mediaType)
        }

        val request = builder.delete(requestBody).url(url).build()
        val call = okHttpClient.newCall(request)

        // auto  thread
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                okHttpWrapperDelegate.onFailure(call, e)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                okHttpWrapperDelegate.onResponse(call, response)
            }
        })
    }

    /**
     * for coroutine
     */
    fun requestDelete(
        url: String,
        header: MutableMap<String, String>? = null,
        formData: MutableMap<String, String>? = null,
        fileList: MutableList<FormDataFile>? = null,
        fileKey: String = "",
        jsonObject: JSONObject? = null
    ): CoroutineResponse {

        val builder = Request.Builder()

        // if header
        header?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }

        val requestBody: RequestBody
        // if form data
        if (formData != null) {

            val multipartBodyBuilder: MultipartBody.Builder = MultipartBody.Builder()
            multipartBodyBuilder.setType(MultipartBody.FORM)

            formData.let {
                ILog.debug(TAG, "add form data")
                for ((key, value) in formData) {
                    ILog.debug(TAG, "$key $value")
                    multipartBodyBuilder.addFormDataPart(key, value)
                }
            }

            if (fileList != null) {
                ILog.debug(TAG, "add files")
                var file: File
                for (i in fileList.indices) {
                    ILog.debug(TAG, fileList[i].fileName)
                    val mediaType: MediaType = if (fileList[i].fileName.endsWith("png")) {
                        "image/png".toMediaType()
                    } else {
                        "image/jpeg".toMediaType()
                    }
                    file = File(fileList[i].filePath)
                    multipartBodyBuilder.addFormDataPart(fileKey, fileList[i].fileName, file.asRequestBody(mediaType))
                }
            }

            requestBody = multipartBodyBuilder.build()
        }
        else {

            val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()
            requestBody = jsonObject?.toString()?.toRequestBody(mediaType) ?: "".toRequestBody(mediaType)
        }

        val request = builder.delete(requestBody).url(url).build()
        val call = okHttpClient.newCall(request)

        val result = call.execute()
        return CoroutineResponse(call, result)
    }
}