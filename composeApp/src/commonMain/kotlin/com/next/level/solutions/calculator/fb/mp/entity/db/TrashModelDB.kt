package com.next.level.solutions.calculator.fb.mp.entity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.next.level.solutions.calculator.fb.mp.entity.ui.TrashModelUI
import com.next.level.solutions.calculator.fb.mp.ui.composable.file.picker.PickerType

@Entity(tableName = "trash_db")
data class TrashModelDB(
  @PrimaryKey @ColumnInfo(name = "path") val path: String,
  @ColumnInfo(name = "type") val type: String,
  @ColumnInfo(name = "name") val name: String,
  @ColumnInfo(name = "folder") val folder: String,
  @ColumnInfo(name = "size") val size: Long,
  @ColumnInfo(name = "date_added") val dateAdded: String,
  @ColumnInfo(name = "date_hidden") val dateHidden: String,
  @ColumnInfo(name = "date_modified") val dateModified: String,
  @ColumnInfo(name = "hidden_path") val hiddenPath: String?,
  @ColumnInfo(name = "duration") val duration: String = "",
) : FileDataDB {
  override fun toUI(): TrashModelUI = TrashModelUI(
    path = path,
    name = name,
    folder = folder,
    size = size,
    dateAdded = dateAdded,
    dateHidden = dateHidden,
    dateModified = dateModified,
    hiddenPath = hiddenPath,
  ).also {
    it.fileType = when (type) {
      PickerType.File.name -> PickerType.File
      PickerType.Note.name -> PickerType.Note
      PickerType.Photo.name -> PickerType.Photo
      PickerType.Trash.name -> PickerType.Trash
      else -> PickerType.Video
    }

    it.duration = duration
  }
}
