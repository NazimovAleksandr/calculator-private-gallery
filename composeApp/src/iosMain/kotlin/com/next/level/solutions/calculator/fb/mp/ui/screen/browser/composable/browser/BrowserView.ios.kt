package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.browser

import coil3.Bitmap
import com.multiplatform.webview.web.NativeWebView
import com.multiplatform.webview.web.PlatformWebViewParams

// todo
actual fun getPlatformWebViewParams(
  onPageStarted: (url: String?, title: String?) -> Unit,
  onPageFinished: (url: String?, title: String?) -> Unit,
  onPageCommitVisible: (url: String?, title: String?) -> Unit,
  onReceivedIcon: (icon: Bitmap?) -> Unit,
  onCreateWindow: (view: NativeWebView, url: String) -> Unit,
): PlatformWebViewParams {
  return PlatformWebViewParams()
}