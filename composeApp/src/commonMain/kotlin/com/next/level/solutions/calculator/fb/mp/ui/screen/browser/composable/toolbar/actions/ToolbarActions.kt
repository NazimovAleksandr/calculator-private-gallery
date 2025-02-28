package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.actions

sealed interface ToolbarActions {
  object Home : ToolbarActions
  object Reload : ToolbarActions
  object AddTab : ToolbarActions
  object History : ToolbarActions
  object InputSearchClear : ToolbarActions
  class Load(val url: String) : ToolbarActions
  class InputSearch(val value: String) : ToolbarActions
  class Menu(val key: String) : ToolbarActions
}