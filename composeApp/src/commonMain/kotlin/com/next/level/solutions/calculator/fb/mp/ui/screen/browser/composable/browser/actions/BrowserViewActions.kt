package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.browser.actions

import coil3.Bitmap
import com.multiplatform.webview.web.NativeWebView

sealed interface BrowserViewActions {
  class OnPageStarted(val url: String?, val  title: String?) : BrowserViewActions
  class OnPageFinished(val url: String?, val  title: String?) : BrowserViewActions
  class OnPageCommitVisible(val url: String?, val  title: String?) : BrowserViewActions
  class OnCreateWindow(val view: NativeWebView, val url: String) : BrowserViewActions
  class OnReceivedIcon(val icon: Bitmap?) : BrowserViewActions
}