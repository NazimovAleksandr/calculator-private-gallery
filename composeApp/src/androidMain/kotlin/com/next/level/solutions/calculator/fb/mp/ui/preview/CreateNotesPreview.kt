package com.next.level.solutions.calculator.fb.mp.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.next.level.solutions.calculator.fb.mp.ui.screen.create.notes.CreateNotesContentPreview
import com.next.level.solutions.calculator.fb.mp.ui.theme.AppThemePreview

@PreviewLightDark
@Composable
private fun Preview() {
  AppThemePreview {
    CreateNotesContentPreview()
  }
}