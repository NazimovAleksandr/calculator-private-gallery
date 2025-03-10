package com.next.level.solutions.calculator.fb.mp.entity.ui

import com.next.level.solutions.calculator.fb.mp.entity.db.PhotoModelDB
import com.next.level.solutions.calculator.fb.mp.entity.db.TrashModelDB
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import com.next.level.solutions.calculator.fb.mp.utils.Logger

data class PhotoModelUI(
  override val path: String,
  override val name: String,
  override val folder: String,
  override val size: Long,
  override val dateAdded: String,
  override val dateHidden: String,
  override val dateModified: String,
  override val hiddenPath: String? = null,
) : FileDataUI {
  override val fileType: PickerType = PickerType.Photo
  override val duration: String = ""
  var url: Any? = null
  var asset: Any? = null

  override fun toString(): String {
    return "${this::class.simpleName}(fileType='$fileType', name='$name')"
  }

  override fun toDB(): PhotoModelDB = PhotoModelDB(
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
    type = fileType.name,
    path = path,
    name = name,
    folder = folder,
    size = size,
    dateAdded = dateAdded,
    dateHidden = dateHidden,
    dateModified = dateModified,
    hiddenPath = hiddenPath,
  ).apply {
    Logger.d("TAG", "PhotoModelUI.toTrashDB.type: $type")
  }
}
