package com.next.level.solutions.calculator.fb.mp.entity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_db")
data class NoteModelDB(
  @PrimaryKey @ColumnInfo(name = "date_added") val dateAdded: String,
  @ColumnInfo(name = "date_modified") val dateModified: String,
  @ColumnInfo(name = "name") val name: String,
  @ColumnInfo(name = "note") val note: String,
  @ColumnInfo(name = "path") val path: String,
)
