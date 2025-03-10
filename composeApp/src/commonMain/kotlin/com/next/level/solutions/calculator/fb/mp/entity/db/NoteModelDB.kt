package com.next.level.solutions.calculator.fb.mp.entity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.next.level.solutions.calculator.fb.mp.entity.ui.NoteModelUI

@Entity(tableName = "notes_db")
data class NoteModelDB(
  @PrimaryKey @ColumnInfo(name = "date_added") val dateAdded: String,
  @ColumnInfo(name = "date_modified") val dateModified: String,
  @ColumnInfo(name = "name") val name: String,
  @ColumnInfo(name = "note") val note: String,
  @ColumnInfo(name = "path") val path: String,
) : FileDataDB {
  override fun toUI(): NoteModelUI = NoteModelUI(
    dateAdded = dateAdded,
    dateModified = dateModified,
    name = name,
    note = note,
    path = path,
  )
}