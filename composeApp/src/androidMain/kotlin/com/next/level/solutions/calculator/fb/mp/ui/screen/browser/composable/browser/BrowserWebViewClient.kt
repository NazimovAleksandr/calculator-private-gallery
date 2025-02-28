package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.browser

import android.graphics.Bitmap
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import com.multiplatform.webview.web.AccompanistWebViewClient

class BrowserWebViewClient(
  private val onPageStarted: (url: String?, title: String?) -> Unit,
  private val onPageFinished: (url: String?, title: String?) -> Unit,
  private val onPageCommitVisible: (url: String?, title: String?) -> Unit,
) : AccompanistWebViewClient() {

  override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
    val url = request?.url.toString()
    return url.contains("intent://")
  }

  @Deprecated("Deprecated in Java", ReplaceWith("url.toString().contains(\"intent://\")"))
  override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
    return url.toString().contains("intent://")
  }

  override fun shouldInterceptRequest(
    view: WebView?,
    request: WebResourceRequest?
  ): WebResourceResponse? {
    return super.shouldInterceptRequest(view, request)
  }

  override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
    super.onPageStarted(view, url, favicon)
    onPageStarted.invoke(url, view.title)
  }

  override fun onPageCommitVisible(view: WebView, url: String?) {
    super.onPageCommitVisible(view, url)
    onPageCommitVisible.invoke(url, view.title)
  }

  override fun onPageFinished(view: WebView, url: String?) {
    super.onPageFinished(view, url)
    onPageFinished.invoke(url, view.title)
  }
}