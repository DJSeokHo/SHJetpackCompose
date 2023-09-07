package com.swein.shjetpackcompose.basic.webviewexample

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.net.http.SslError
import android.os.Message
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout.LayoutParams
import java.net.URISyntaxException

class WebViewHolder(
    val context: Context
) {

    companion object {
        private const val TAG = "WebViewHolder"
    }

    private var onPageFinished: ((String) -> Unit)? = null
    private var onLoadUrlAndOverrideCondition: ((String) -> Boolean)? = null
    private var onError: ((String) -> Unit)? = null
    private var onConsoleMessageDebugDescription : ((String) -> Unit)? = null

    var webView: WebView = WebView(context)

    private var webChromeClient = object : WebChromeClient() {

        override fun onCreateWindow(
            view: WebView?,
            isDialog: Boolean,
            isUserGesture: Boolean,
            resultMsg: Message?
        ): Boolean {
            Log.d(TAG, "onCreateWindow")

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
            onConsoleMessageDebugDescription?.let {
                consoleMessage?.let {
                    it("web view console message debug: ${consoleMessage.message()} ${consoleMessage.lineNumber()} ${consoleMessage.sourceId()} $consoleMessage")
                }
            }
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
            onError?.let {
                it("onReceivedError ${request?.url} ${error?.errorCode} ${error?.description}")
            }
        }

        override fun onReceivedHttpError(
            view: WebView?,
            request: WebResourceRequest?,
            errorResponse: WebResourceResponse?
        ) {
            super.onReceivedHttpError(view, request, errorResponse)
            onError?.let {
                it("onReceivedHttpError ${request?.url} ${errorResponse?.reasonPhrase} ${errorResponse?.statusCode}")
            }
        }

        override fun onReceivedSslError(
            view: WebView?,
            handler: SslErrorHandler?,
            error: SslError?
        ) {
            super.onReceivedSslError(view, handler, error)
            onError?.let {
                it("onReceivedSslError $handler ${error?.url} $error")
            }
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
                    webView.context.startActivity(Intent(Intent.ACTION_VIEW, uri))
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
                        webView.context.startActivity(
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
            Log.d(TAG, "shouldOverrideUrlLoading")
//            return super.shouldOverrideUrlLoading(view, url)
            val result = loadUrlAndOverride(url)
            Log.d(TAG, "$result")
            return result
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            Log.d(TAG, "onPageStarted $url")
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            Log.d(TAG, "onPageFinished $url")
            onPageFinished?.let {
                url?.let {
                    it(url)
                }
            }
        }

    }

    init {

        webView.layoutParams = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        initWebView()
    }

    fun setDelegate(
        onPageFinished: ((String) -> Unit)? = null,
        onLoadUrlAndOverrideCondition: ((String) -> Boolean)? = null,
        onError: ((String) -> Unit)? = null,
        onConsoleMessageDebugDescription: ((String) -> Unit)? = null) {
        this.onPageFinished = onPageFinished
        this.onLoadUrlAndOverrideCondition = onLoadUrlAndOverrideCondition
        this.onError = onError
        this.onConsoleMessageDebugDescription = onConsoleMessageDebugDescription
    }

    fun setTransparent() {
        webView.setBackgroundColor(0)
        webView.background.alpha = 0
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    private fun initWebView() {
        setupWebViewSetting()
        setupWebChromeClient()
        setupWebViewClient()
    }

    private fun loadUrlAndOverride(url: String?): Boolean {

        url?.let {
            Log.d(TAG, url)
            onLoadUrlAndOverrideCondition?.let {
                if (it(url)) {
                    return true
                }
            }

            webView.loadUrl(url)
        }

        return true
    }

    @SuppressLint("JavascriptInterface")
    fun setupWebViewJavascript(`object`: Any?, name: String?) {
        webView.addJavascriptInterface(`object`!!, name!!)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebViewSetting() {
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

    private fun setupWebChromeClient() {
        webView.webChromeClient = webChromeClient
    }

    private fun setupWebViewClient() {
        webView.webViewClient = webViewClient
    }

    fun loadUrl(url: String) {
        if (url.endsWith(".pdf")) {
            val data =
                "<iframe src='http://docs.google.com/gview?embedded=true&url=$url' width='100%' height='100%' style='border: none;'></iframe>"
            webView.loadData(data, "text/html", "UTF-8")
        }
        else {
            webView.loadUrl(url)
        }
    }

    fun loadHtml(htmlTag: String, matchViewPort: Boolean = true) {
        val js = "<script type='text/javascript'> window.onload = function() {var ${'$'}img = document.getElementsByTagName('img');for(var p in  ${'$'}img){${'$'}img[p].style.width = '100%'; ${'$'}img[p].style.height ='auto'}}</script>"
        val meta = if (matchViewPort) {
            "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no,viewport-fit=cover\">"
        }
        else {
            ""
        }
        webView.loadDataWithBaseURL(null, "$js$htmlTag$meta", "text/html", "UTF-8", null)
    }

    fun postUrl(url: String, postData: ByteArray) {
        webView.postUrl(url, postData)
    }

    fun evaluateJavascript(url: String) {
        webView.evaluateJavascript(
            url
        ) { value -> Log.d(TAG, value) }
    }

    fun reload() {
        webView.reload()
    }

    fun stopLoading() {
        webView.stopLoading()
    }

    fun canGoBack(): Boolean {
        return webView.canGoBack()
    }

    fun goBack() {
        if (webView.canGoBack()) {
            webView.goBack()
        }
    }

    fun destroyWebView() {
        Log.d(TAG, "destroyWebView")
        webView.webChromeClient = null
//        webChromeClient = null
//        webView.setWebViewClient(null)
//        webViewClient = null
        webView.stopLoading()
//        webView = null
    }

    protected fun finalize() {
        Log.d(TAG, "finalize")
    }
}