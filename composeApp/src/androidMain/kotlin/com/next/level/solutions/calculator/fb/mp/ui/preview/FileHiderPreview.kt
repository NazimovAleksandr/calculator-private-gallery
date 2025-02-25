package com.next.level.solutions.calculator.fb.mp.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.next.level.solutions.calculator.fb.mp.ui.screen.file.hider.FileHiderContentPreview
import com.next.level.solutions.calculator.fb.mp.ui.screen.file.hider.composable.SpinnerPreview
import com.next.level.solutions.calculator.fb.mp.ui.theme.AppThemePreview

@PreviewLightDark
@Composable
private fun Spinner_Preview() {
  AppThemePreview {
    SpinnerPreview()
  }
}

@PreviewLightDark
@Composable
private fun FileHiderContent_Preview() {
  AppThemePreview {
    FileHiderContentPreview()
  }
}