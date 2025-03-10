package com.next.level.solutions.calculator.fb.mp.entity.ui

import com.next.level.solutions.calculator.fb.mp.entity.db.TrashModelDB
import com.next.level.solutions.calculator.fb.mp.entity.db.VideoModelDB
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType

data class VideoModelUI(
  override val path: String,
  override val name: String,
  override val folder: String,
  override val size: Long,
  override val dateAdded: String,
  override val dateHidden: String,
  override val dateModified: String,
  override val hiddenPath: String? = null,
) : FileDataUI {
  override val fileType: PickerType = PickerType.Video
  override var duration: String = ""

  override fun toString(): String {
    return "${this::class.simpleName}(fileType='$fileType', name='$name')"
  }

  override fun toDB(): VideoModelDB = VideoModelDB(
    path = path,
    name = name,
    folder = folder,
    size = size,
    dateAdded = dateAdded,
    dateHidden = dateHidden,
    dateModified = dateModified,
    hiddenPath = hiddenPath,
    duration = when (duration) {
//    "" -> updateDuration()
      else -> duration
    },
  )

  override fun toTrashDB(): TrashModelDB = TrashModelDB(
    type = PickerType.Video.name,
    path = path,
    name = name,
    folder = folder,
    size = size,
    dateAdded = dateAdded,
    dateHidden = dateHidden,
    dateModified = dateModified,
    hiddenPath = hiddenPath,
    duration = duration,
  )
}
