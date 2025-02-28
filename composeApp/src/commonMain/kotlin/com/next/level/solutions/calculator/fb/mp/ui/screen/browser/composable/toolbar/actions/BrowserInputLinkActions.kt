package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.actions

interface BrowserInputLinkActions {
  object ClickReload : BrowserInputLinkActions
  class OpenLinkEditor(val focused: Boolean) : BrowserInputLinkActions
  class ClickLoad(val url: String) : BrowserInputLinkActions
  class Search(val url: String) : BrowserInputLinkActions
}