package com.next.level.solutions.calculator.fb.mp.entity.ui

import com.next.level.solutions.calculator.fb.mp.entity.db.NoteModelDB
import com.next.level.solutions.calculator.fb.mp.entity.db.TrashModelDB
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType
import kotlinx.serialization.Serializable

@Serializable
data class NoteModelUI(
  override val dateAdded: String,
  override val dateModified: String = dateAdded,
  override val path: String = dateAdded,
  override val name: String,
  val note: String,
) : FileDataUI {
  override val fileType: PickerType = PickerType.Note
  override val folder: String = ""
  override val size: Long = 0
  override val duration: String = ""
  override val dateHidden: String = ""
  override val hiddenPath: String? = null

  override fun toString(): String {
    return "${this::class.simpleName}(fileType='$fileType', name='$name')"
  }

  override fun toDB(): NoteModelDB = NoteModelDB(
    dateAdded = dateAdded,
    dateModified = dateModified,
    path = path,
    name = name,
    note = note,
  )

  override fun toTrashDB(): TrashModelDB = TrashModelDB(
    type = PickerType.Note.name,
    dateAdded = dateAdded,
    dateModified = dateModified,
    path = path,
    name = name,
    folder = note,

    size = size,
    dateHidden = dateHidden,
    hiddenPath = hiddenPath,
    duration = duration,
  )
}
