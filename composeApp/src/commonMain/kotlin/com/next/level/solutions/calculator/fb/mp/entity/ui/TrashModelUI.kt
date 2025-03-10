package com.next.level.solutions.calculator.fb.mp.entity.ui

import com.next.level.solutions.calculator.fb.mp.entity.db.TrashModelDB
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType

data class TrashModelUI(
  override val path: String,
  override val name: String,
  override val folder: String,
  override val size: Long,
  override val dateAdded: String,
  override val dateHidden: String,
  override val dateModified: String,
  override val hiddenPath: String? = null,
) : FileDataUI {
  override var fileType: PickerType = PickerType.Trash
  override var duration: String = ""

  override fun toString(): String {
    return "${this::class.simpleName}(fileType='$fileType', name='$name')"
  }

  override fun toDB(): TrashModelDB = TrashModelDB(
    path = path,
    name = name,
    folder = folder,
    size = size,
    dateAdded = dateAdded,
    dateHidden = dateHidden,
    dateModified = dateModified,
    hiddenPath = hiddenPath,
    duration = duration,
    type = when (fileType) {
      PickerType.File -> PickerType.File.name
      PickerType.Note -> PickerType.Note.name
      PickerType.Photo -> PickerType.Photo.name
      PickerType.Trash -> PickerType.Trash.name
      PickerType.Video -> PickerType.Video.name
    }
  )

  override fun toTrashDB(): TrashModelDB = throw Exception(
    "TrashModelUI cannot be converted to TrashModelDB with method toTrashDB()",
  )

  fun toFileUI(): FileModelUI = FileModelUI(
    path = path,
    name = name,
    folder = folder,
    size = size,
    dateAdded = dateAdded,
    dateHidden = dateHidden,
    dateModified = dateModified,
    hiddenPath = hiddenPath,
  )

  fun toNoteUI(): NoteModelUI = NoteModelUI(
    dateAdded = dateAdded,
    dateModified = dateModified,
    path = path,
    name = name,
    note = folder,
  )

  fun toPhotoUI(): PhotoModelUI = PhotoModelUI(
    path = path,
    name = name,
    folder = folder,
    size = size,
    dateAdded = dateAdded,
    dateHidden = dateHidden,
    dateModified = dateModified,
    hiddenPath = hiddenPath,
  )

  fun toVideoUI(): VideoModelUI = VideoModelUI(
    path = path,
    name = name,
    folder = folder,
    size = size,
    dateAdded = dateAdded,
    dateHidden = dateHidden,
    dateModified = dateModified,
    hiddenPath = hiddenPath,
  ).also {
    it.duration = duration
  }
}