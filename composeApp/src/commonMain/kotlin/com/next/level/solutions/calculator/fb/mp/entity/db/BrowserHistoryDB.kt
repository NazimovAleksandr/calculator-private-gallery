package com.next.level.solutions.calculator.fb.mp.entity.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "browser_history")
data class BrowserHistoryDB(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: Long,
    @PrimaryKey(autoGenerate = true) val id: Int?,
)