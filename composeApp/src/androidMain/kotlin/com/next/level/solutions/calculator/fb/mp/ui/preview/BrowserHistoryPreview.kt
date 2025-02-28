package com.next.level.solutions.calculator.fb.mp.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser_history.BrowserHistoryContentPreview
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser_history.composable.HistoryCardPreview
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser_history.dialog.DeleteAllDialogPreview
import com.next.level.solutions.calculator.fb.mp.ui.theme.AppThemePreview

@PreviewLightDark
@Composable
private fun BrowserHistoryContent_Preview() {
  AppThemePreview {
    BrowserHistoryContentPreview()
  }
}

@PreviewLightDark
@Composable
private fun HistoryCard_Preview() {
  AppThemePreview {
    HistoryCardPreview()
  }
}

@PreviewLightDark
@Composable
private fun DeleteAllDialog_Preview() {
  AppThemePreview {
    DeleteAllDialogPreview()
  }
}