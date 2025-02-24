package com.next.level.solutions.calculator.fb.mp.entity.ui

import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType

data class ParentFolderModelUI(
  override val path: String,
  override val name: String,
  override val folder: String = "",
  override val size: Long = 0,
  override val dateAdded: String = "",
  override val dateHidden: String = "",
  override val dateModified: String = "",
  override val hiddenPath: String? = null,
) : FileDataUI {
  override var fileType: FilePickerFileType = FilePickerFileType.File
  override var duration: String = ""
}
