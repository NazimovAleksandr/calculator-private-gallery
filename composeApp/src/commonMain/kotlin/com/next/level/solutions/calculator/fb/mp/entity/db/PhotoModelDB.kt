package com.next.level.solutions.calculator.fb.mp.entity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.next.level.solutions.calculator.fb.mp.entity.ui.PhotoModelUI

@Entity(tableName = "photos_db")
data class PhotoModelDB(
  @PrimaryKey @ColumnInfo(name = "path") val path: String,
  @ColumnInfo(name = "name") val name: String,
  @ColumnInfo(name = "folder") val folder: String,
  @ColumnInfo(name = "size") val size: Long,
  @ColumnInfo(name = "date_added") val dateAdded: String,
  @ColumnInfo(name = "date_hidden") val dateHidden: String,
  @ColumnInfo(name = "date_modified") val dateModified: String,
  @ColumnInfo(name = "hidden_path") val hiddenPath: String?,
) : FileDataDB {
  override fun toUI(): PhotoModelUI = PhotoModelUI(
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
