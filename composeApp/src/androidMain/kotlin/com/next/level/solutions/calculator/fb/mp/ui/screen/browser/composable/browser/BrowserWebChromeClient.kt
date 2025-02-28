package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.browser

import android.graphics.Bitmap
import android.os.Message
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.multiplatform.webview.web.AccompanistWebChromeClient

class BrowserWebChromeClient(
  private val onReceivedIcon: (Bitmap?) -> Unit,
  private val onShowCustomView: (View?) -> Unit,
  private val onHideCustomView: () -> Unit,
  private val onCreateWindow: (WebView, String) -> Unit,
) : AccompanistWebChromeClient() {

  override fun onReceivedIcon(view: WebView, icon: Bitmap?) {
    super.onReceivedIcon(view, icon)
    onReceivedIcon(icon)
  }

  override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
    onShowCustomView(view)
  }

  override fun onHideCustomView() {
    onHideCustomView.invoke()
  }

  override fun onCreateWindow(
    view: WebView?,
    isDialog: Boolean,
    isUserGesture: Boolean,
    resultMsg: Message?,
  ): Boolean {
    val context = view?.context ?: return false
    resultMsg ?: return false

    val newWebView = WebView(context)
    val transport = resultMsg.obj as WebView.WebViewTransport

    newWebView.setWebViewClient(object : WebViewClient() {
      override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        onCreateWindow(newWebView, request?.url.toString())
        return super.shouldOverrideUrlLoading(view, request)
      }
    })

    transport.webView = newWebView
    resultMsg.sendToTarget()

    return true
  }
}