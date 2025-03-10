package com.next.level.solutions.calculator.fb.mp.entity.ui

import com.next.level.solutions.calculator.fb.mp.entity.db.FileModelDB
import com.next.level.solutions.calculator.fb.mp.entity.db.TrashModelDB
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType

data class FileModelUI(
  override val path: String,
  override val name: String,
  override val folder: String,
  override val size: Long,
  override val dateAdded: String,
  override val dateHidden: String,
  override val dateModified: String,
  override val hiddenPath: String? = null,
) : FileDataUI {
  override val fileType: PickerType = PickerType.File
  override val duration: String = ""

  override fun toString(): String {
    return "${this::class.simpleName}(fileType='$fileType', name='$name')"
  }

  override fun toDB(): FileModelDB = FileModelDB(
    path = path,
    name = name,
    folder = folder,
    size = size,
    dateAdded = dateAdded,
    dateHidden = dateHidden,
    dateModified = dateModified,
    hiddenPath = hiddenPath,
  )

  override fun toTrashDB(): TrashModelDB = TrashModelDB(
    type = PickerType.File.name,
    path = path,
    name = name,
    folder = folder,
    size = size,
    dateAdded = dateAdded,
    dateHidden = dateHidden,
    dateModified = dateModified,
    hiddenPath = hiddenPath,
  )
}
