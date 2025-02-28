package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.browser.actions

sealed interface BrowserWebChromeClientActions {
  object HideCustomView : BrowserWebChromeClientActions
  class ShowCustomView/*(val view: View?)*/ : BrowserWebChromeClientActions
  class ReceivedTitle(val title: String?) : BrowserWebChromeClientActions
}