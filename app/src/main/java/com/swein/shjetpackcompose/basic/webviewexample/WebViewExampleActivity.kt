package com.swein.shjetpackcompose.basic.webviewexample

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.os.Message
import android.webkit.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.WebContent
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.swein.framework.utility.debug.ILog
import java.net.URISyntaxException

class WebViewExampleActivity : ComponentActivity() {

    companion object {
        private const val TAG = "WebViewExampleActivity"
    }

    private var webChromeClient = object : WebChromeClient() {

        override fun onCreateWindow(
            view: WebView?,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {
            ILog.debug(TAG, "onCreateWindow")

            view?.webChromeClient = this

            val transport = resultMsg?.obj as WebView.WebViewTransport
            transport.webView = view
            resultMsg.sendToTarget()

            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
        }

        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
        }

        override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {

            ILog.debug(TAG,
                "web view console message debug: ${consoleMessage?.message()} ${consoleMessage?.lineNumber()} ${consoleMessage?.sourceId()} $consoleMessage"
            )
            return super.onConsoleMessage(consoleMessage)
        }

        override fun onJsAlert(
            view: WebView?,
            url: String?,
            message: String?,
            result: JsResult?
        ): Boolean {

//            custom alert dialog
//            AlertDialog.Builder(view?.context).setTitle("").setMessage(message).setPositiveButton(android.R.string.ok) { _, _ ->
//                result?.confirm()
//            }.setCancelable(false).create().show()
//            return true

            return super.onJsAlert(view, url, message, result)
        }

        override fun onJsConfirm(
            view: WebView?,
            url: String?,
            message: String?,
            result: JsResult?
        ): Boolean {
            return super.onJsConfirm(view, url, message, result)
        }

        override fun onGeolocationPermissionsShowPrompt(
            origin: String?,
            callback: GeolocationPermissions.Callback?
        ) {
            super.onGeolocationPermissionsShowPrompt(origin, callback)
            callback?.invoke(origin, true, false)
        }

    }

    private var webViewClient = object : WebViewClient() {

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            super.onReceivedError(view, request, error)
            ILog.debug(TAG, "onReceivedError ${request?.url} ${error?.errorCode} ${error?.description}")
        }

        override fun onReceivedHttpError(
            view: WebView?,
            request: WebResourceRequest?,
            errorResponse: WebResourceResponse?
        ) {
            super.onReceivedHttpError(view, request, errorResponse)
            ILog.debug(TAG, "onReceivedHttpError ${request?.url} ${errorResponse?.reasonPhrase} ${errorResponse?.statusCode}")
        }

        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            super.onReceivedSslError(view, handler, error)
            ILog.debug(TAG, "onReceivedSslError $handler ${error?.url} $error")
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {

            if (!request?.url.toString().startsWith("http://") && !request?.url.toString()
                    .startsWith("https://") && !request?.url.toString().startsWith("javascript")
            ) {
                var intent: Intent? = null
                return try {
                    intent = Intent.parseUri(request?.url.toString(), Intent.URI_INTENT_SCHEME)
                    val uri = Uri.parse(intent.dataString)
                    view?.context?.startActivity(Intent(Intent.ACTION_VIEW, uri))
                    true
                }
                catch (e: URISyntaxException) {
                    e.printStackTrace()
                    false
                }
                catch (e: ActivityNotFoundException) {
                    if (intent == null) {
                        return false
                    }
                    val packageName = intent.getPackage()
                    if (packageName != null) {
                        view?.context?.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$packageName")
                            )
                        )
                        return true
                    }
                    false
                }
            }

            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            ILog.debug(TAG, "shouldOverrideUrlLoading")
//            return super.shouldOverrideUrlLoading(view, url)
//            val result = loadUrlAndOverride(url)
//            Log.d(TAG, "$result")
//            return result
            return true
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            ILog.debug(TAG, "onPageStarted $url")
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            ILog.debug(TAG, "onPageFinished $url")
//            onPageFinished?.let {
//                url?.let {
//                    it(url)
//                }
//            }
        }
    }

//    private fun loadUrlAndOverride(url: String?): Boolean {
//
//        url?.let {
//            Log.d(TAG, url)
//            onLoadUrlAndOverrideCondition?.let {
//                if (it(url)) {
//                    return true
//                }
//            }
//
//            webView.loadUrl(url)
//        }
//
//        return true
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ContentView()
        }
    }

    @Composable
    private fun ContentView() {

        val state = rememberWebViewState(url = "https://www.google.com")
        val (textFieldValue, setTextFieldValue) = remember(state.content) {
            mutableStateOf(state.content.getCurrentUrl() ?: "")
        }

        Column {
            Row {
                Box(modifier = Modifier.weight(1f)) {
                    if (state.errorsForCurrentRequest.isNotEmpty()) {
                        Image(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            colorFilter = ColorFilter.tint(Color.Red),
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(8.dp)
                        )
                    }

                    OutlinedTextField(
                        value = textFieldValue,
                        onValueChange = setTextFieldValue,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Button(
                    onClick = {
                        state.content = WebContent.Url(textFieldValue)
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text("Go")
                }
            }

            if (state.isLoading) {
                LinearProgressIndicator(Modifier.fillMaxWidth())
            }

            WebView(
                state = state,
                modifier = Modifier.weight(1f),
                onCreated = { webView ->
                    initWebView(webView = webView)
                }
            )
        }

    }

    private fun initWebView(webView: WebView) {
        setupWebViewSetting(webView)
        setupWebChromeClient(webView)
        setupWebViewClient(webView)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebViewSetting(webView: WebView) {
        val webSettings = webView.settings
        // you can choose what you need
        webSettings.domStorageEnabled = true
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.allowFileAccess = true
        webSettings.allowContentAccess = true
        webSettings.setSupportZoom(true)
//        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.loadsImagesAutomatically = true
        webSettings.mediaPlaybackRequiresUserGesture = false
        WebView.setWebContentsDebuggingEnabled(true)
    }

    private fun setupWebChromeClient(webView: WebView) {
        webView.webChromeClient = webChromeClient
    }

    private fun setupWebViewClient(webView: WebView) {
        webView.webViewClient = webViewClient
    }
}