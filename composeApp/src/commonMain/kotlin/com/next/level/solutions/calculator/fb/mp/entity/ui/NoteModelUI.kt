package com.next.level.solutions.calculator.fb.mp.entity.ui

import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType

data class NoteModelUI(
  override val dateAdded: String,
  override val name: String,
  val note: String,
  override val dateModified: String = dateAdded,
  override val path: String = dateAdded,
) : FileDataUI {
  override val fileType: FilePickerFileType = FilePickerFileType.Note
  override val folder: String = ""
  override val size: Long = 0
  override val duration: String = ""
  override val dateHidden: String = ""
  override val hiddenPath: String? = null
}
