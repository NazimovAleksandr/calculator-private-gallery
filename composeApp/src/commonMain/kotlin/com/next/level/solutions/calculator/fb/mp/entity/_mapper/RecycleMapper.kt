package com.next.level.solutions.calculator.fb.mp.entity._mapper

import com.next.level.solutions.calculator.fb.mp.entity.db.TrashModelDB
import com.next.level.solutions.calculator.fb.mp.entity.ui.TrashModelUI
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.FilePickerFileType

fun TrashModelDB.toUI(): TrashModelUI = TrashModelUI(
  path = path,
  name = name,
  folder = folder,
  size = size,
  dateAdded = dateAdded,
  dateHidden = dateHidden,
  dateModified = dateModified,
  hiddenPath = hiddenPath,
).apply {
  fileType = when (this@toUI.type) {
    FilePickerFileType.File.name -> FilePickerFileType.File
    FilePickerFileType.Photo.name -> FilePickerFileType.Photo
    FilePickerFileType.Trash.name -> FilePickerFileType.Trash
    else -> FilePickerFileType.Video
  }

  duration = this@toUI.duration
}