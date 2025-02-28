package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.browser.web.chrome.client
//
//import com.multiplatform.webview.web.WebView
//
//class BrowserWebChromeClient(
//    private val showCustomView: (View?) -> Unit,
//    private val hideCustomView: () -> Unit,
//    private val createWindow: (WebView, String) -> Unit,
//    private val receivedIcon: (Bitmap?) -> Unit,
//    private val receivedTitle: (String?) -> Unit,
//) : AccompanistWebChromeClient() {
//    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
//        showCustomView(view)
//    }
//
//    override fun onHideCustomView() {
//        hideCustomView()
//    }
//
//    override fun onCreateWindow(
//        view: WebView?,
//        isDialog: Boolean,
//        isUserGesture: Boolean,
//        resultMsg: Message?,
//    ): Boolean {
//        val context = view?.context ?: return false
//        resultMsg ?: return false
//
//        val newWebView = WebView(context)
//        val transport = resultMsg.obj as WebViewTransport
//
//        newWebView.setWebViewClient(object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//                createWindow(newWebView, request?.url.toString())
//                return super.shouldOverrideUrlLoading(view, request)
//            }
//        })
//
//        transport.webView = newWebView
//        resultMsg.sendToTarget()
//
//        return true
//    }
//
//    override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
//        super.onReceivedIcon(view, icon)
//        receivedIcon(icon)
//    }
//
//    override fun onReceivedTitle(view: WebView?, title: String?) {
//        super.onReceivedTitle(view, title)
//        receivedTitle(title)
//    }
//
//    /*override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
//        Log.d(
//            "TAG_WebViewConsole",
//            """
//                message = ${consoleMessage?.message()}
//            """.trimIndent()
////                sourceId = ${consoleMessage?.sourceId()}
////                lineNumber = ${consoleMessage?.lineNumber()}
//        )
//
//        return super.onConsoleMessage(consoleMessage)
//    }*/
//}