package com.swein.shjetpackcompose.basic.webviewexample

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Bundle
import android.os.Message
import android.webkit.*
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.WebContent
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.rememberWebViewState

class WebViewExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            ContentView()
        }
    }

    @Composable
    private fun ContentView() {

        val state = rememberWebViewState(url = "https://www.google.com")

        val (url, onTextFieldValueChange) = remember(state.content) {
            mutableStateOf(state.content.getCurrentUrl() ?: "")
        }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            TopPart(state, url, onTextFieldValueChange)

            WebViewPart(state)
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun TopPart(webViewState: WebViewState, url: String, onTextFieldValueChange: (value: String) -> Unit) {

        val keyboardController = LocalSoftwareKeyboardController.current

        Box(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().background(Color.White)
        ) {

            OutlinedTextField(
                value = url,
                onValueChange = onTextFieldValueChange,
                modifier = Modifier.padding(top = 6.dp, bottom = 6.dp, start = 10.dp, end = 80.dp),
                trailingIcon = {
                    if (webViewState.errorsForCurrentRequest.isNotEmpty()) {
                        Image(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color.Red),
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    webViewState.content = WebContent.Url("https://")
                                }
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Go),
                keyboardActions = KeyboardActions(
                    onGo = {
                        // on go
                        webViewState.content = WebContent.Url(url)
                        keyboardController?.hide()
                    }
                ),
                maxLines = 1,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.DarkGray,
                    backgroundColor = Color.White,
                    cursorColor = Color.Black
                ),
                shape = MaterialTheme.shapes.medium
            )

            Button(
                onClick = {

                    // on go
                    webViewState.content = WebContent.Url(url)
                    keyboardController?.hide()
                },
                modifier = Modifier.width(70.dp).align(Alignment.CenterEnd).padding(end = 10.dp),
                colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.Red)
            ) {
                Text("Go")
            }
        }
    }

    @Composable
    private fun WebViewPart(webViewState: WebViewState) {

        if (webViewState.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Red
            )
        }

        WebView(
            state = webViewState,
            modifier = Modifier.fillMaxSize(),
            onCreated = { webView ->
                initWebView(webView = webView)
            }
        )
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
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.loadsImagesAutomatically = true
        webSettings.mediaPlaybackRequiresUserGesture = false
        WebView.setWebContentsDebuggingEnabled(true)
    }

    private fun setupWebChromeClient(webView: WebView) {
        webView.webChromeClient = object : WebChromeClient() {

            override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message?
            ): Boolean {

                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg)
            }

            override fun onReceivedTitle(view: WebView?, title: String?) {
                super.onReceivedTitle(view, title)
            }

            override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {

                return super.onConsoleMessage(consoleMessage)
            }

            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {

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
    }

    private fun setupWebViewClient(webView: WebView) {
        webView.webViewClient = object : WebViewClient() {

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
            }

            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                super.onReceivedHttpError(view, request, errorResponse)
            }

            override fun onReceivedSslError(
                view: WebView?,
                handler: SslErrorHandler?,
                error: SslError?
            ) {
                super.onReceivedSslError(view, handler, error)
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

            }
        }
    }
}