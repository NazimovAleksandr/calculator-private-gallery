package com.next.level.solutions.calculator.fb.mp.entity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos_db")
data class VideoModelDB(
  @PrimaryKey @ColumnInfo(name = "path") val path: String,
  @ColumnInfo(name = "name") val name: String,
  @ColumnInfo(name = "folder") val folder: String,
  @ColumnInfo(name = "size") val size: Long,
  @ColumnInfo(name = "duration") val duration: String,
  @ColumnInfo(name = "date_added") val dateAdded: String,
  @ColumnInfo(name = "date_hidden") val dateHidden: String,
  @ColumnInfo(name = "date_modified") val dateModified: String,
  @ColumnInfo(name = "hidden_path") val hiddenPath: String?,
)
