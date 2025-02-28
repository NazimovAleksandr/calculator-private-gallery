package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.browser

import android.graphics.Bitmap
import com.multiplatform.webview.web.NativeWebView
import com.multiplatform.webview.web.PlatformWebViewParams
import com.next.level.solutions.calculator.fb.mp.MainActivity

actual fun getPlatformWebViewParams(
  onPageStarted: (url: String?, title: String?) -> Unit,
  onPageFinished: (url: String?, title: String?) -> Unit,
  onPageCommitVisible: (url: String?, title: String?) -> Unit,
  onReceivedIcon: (icon: Bitmap?) -> Unit,
  onCreateWindow: (view: NativeWebView, url: String) -> Unit,
): PlatformWebViewParams {
  return PlatformWebViewParams(
    client = BrowserWebViewClient(
      onPageStarted = onPageStarted,
      onPageFinished = onPageFinished,
      onPageCommitVisible = onPageCommitVisible,
    ),
    chromeClient = BrowserWebChromeClient(
      onReceivedIcon = onReceivedIcon,
      onCreateWindow = onCreateWindow,
      onShowCustomView = { MainActivity.showCustomView?.invoke(it) },
      onHideCustomView = { MainActivity.hideCustomView?.invoke() },
    ),
  )
}