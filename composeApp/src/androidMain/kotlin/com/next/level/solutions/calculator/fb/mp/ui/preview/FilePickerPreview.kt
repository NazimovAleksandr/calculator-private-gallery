package com.next.level.solutions.calculator.fb.mp.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerPreview
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FolderContentPreview
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.GalleryContentPreview
import com.next.level.solutions.calculator.fb.mp.ui.theme.AppThemePreview

@PreviewLightDark
@Composable
private fun Preview() {
  AppThemePreview {
    FilePickerPreview()
  }
}

@PreviewLightDark
@Composable
private fun Gallery_Preview() {
  AppThemePreview {
    GalleryContentPreview()
  }
}

@PreviewLightDark
@Composable
private fun Folder_Preview() {
  AppThemePreview {
    FolderContentPreview()
  }
}