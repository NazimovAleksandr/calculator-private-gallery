package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.browser.actions

sealed interface BrowserWebViewClientActions {
  object OnErrorConnection : BrowserWebViewClientActions

  class UpdateUrl(val url: String) : BrowserWebViewClientActions
  class UrlLoading(val url: String, val redirect: Boolean) : BrowserWebViewClientActions
  class LoadResource(val url: String, val title: String) : BrowserWebViewClientActions
}