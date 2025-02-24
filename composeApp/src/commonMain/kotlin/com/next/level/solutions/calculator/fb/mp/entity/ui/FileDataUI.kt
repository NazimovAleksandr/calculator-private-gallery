package com.next.level.solutions.calculator.fb.mp.entity.ui

import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType

sealed interface FileDataUI {
  val fileType: FilePickerFileType
  val path: String
  val name: String
  val folder: String
  val size: Long
  val duration: String
  val dateAdded: String
  val dateHidden: String
  val dateModified: String
  val hiddenPath: String?
}

expect fun FileDataUI?.toPath(
  parentFolderPath: String?,
): List<FileDataUI>
