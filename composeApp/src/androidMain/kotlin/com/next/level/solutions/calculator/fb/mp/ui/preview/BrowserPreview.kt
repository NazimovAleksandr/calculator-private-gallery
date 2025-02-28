package com.next.level.solutions.calculator.fb.mp.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.BrowserContentPreview
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.BrowserLinkEditorPreview
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.BrowserToolbarPreview
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.InputLinkFocusedPreview
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.InputLinkUnfocusedPreview
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable.toolbar.LinkTextFieldPreview
import com.next.level.solutions.calculator.fb.mp.ui.theme.AppThemePreview

@PreviewLightDark
@Composable
private fun BrowserContent_Preview() {
  AppThemePreview {
    BrowserContentPreview()
  }
}

@PreviewLightDark
@Composable
private fun BrowserToolbar_Preview() {
  AppThemePreview {
    BrowserToolbarPreview()
  }
}

@PreviewLightDark
@Composable
private fun BrowserLinkEditor_Preview() {
  AppThemePreview {
    BrowserLinkEditorPreview()
  }
}

@PreviewLightDark
@Composable
private fun InputLinkUnfocused_Preview() {
  AppThemePreview {
    InputLinkUnfocusedPreview()
  }
}

@PreviewLightDark
@Composable
private fun InputLinkFocused_Preview() {
  AppThemePreview {
    InputLinkFocusedPreview()
  }
}

@PreviewLightDark
@Composable
private fun LinkTextField_Preview() {
  AppThemePreview {
    LinkTextFieldPreview()
  }
}