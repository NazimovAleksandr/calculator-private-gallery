package com.next.level.solutions.calculator.fb.mp.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.conposable.EmptyListPreview
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerAction
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.HiddenFilesContentPreview
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.conposable.FilesPreview
import com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.dialog.ChooseDialogContentPreview
import com.next.level.solutions.calculator.fb.mp.ui.theme.AppThemePreview

@PreviewLightDark
@Composable
private fun Preview() {
  AppThemePreview {
    HiddenFilesContentPreview()
  }
}

@PreviewLightDark
@Composable
private fun ChooseDialogPreview() {
  AppThemePreview {
    ChooseDialogContentPreview()
  }
}

@PreviewLightDark
@Composable
private fun FilesClickPreview() {
  AppThemePreview {
    FilesPreview(
      initMode = PickerAction.Click,
    )
  }
}

@PreviewLightDark
@Composable
private fun FilesSelectPreview() {
  AppThemePreview {
    FilesPreview(
      initMode = PickerAction.Select,
    )
  }
}

@PreviewLightDark
@Composable
private fun EmptyListPreviewPreview() {
  AppThemePreview {
    EmptyListPreview(
      fileType = PickerType.Photo,
    )
  }
}