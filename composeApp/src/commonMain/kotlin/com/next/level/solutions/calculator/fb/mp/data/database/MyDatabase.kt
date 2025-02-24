package com.next.level.solutions.calculator.fb.mp.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.next.level.solutions.calculator.fb.mp.data.database.dao.BrowserHistoryDao
import com.next.level.solutions.calculator.fb.mp.data.database.dao.FileDao
import com.next.level.solutions.calculator.fb.mp.data.database.dao.NoteDao
import com.next.level.solutions.calculator.fb.mp.data.database.dao.PhotoDao
import com.next.level.solutions.calculator.fb.mp.data.database.dao.TrashDao
import com.next.level.solutions.calculator.fb.mp.data.database.dao.VideoDao
import com.next.level.solutions.calculator.fb.mp.entity.db.BrowserHistoryDB
import com.next.level.solutions.calculator.fb.mp.entity.db.FileModelDB
import com.next.level.solutions.calculator.fb.mp.entity.db.NoteModelDB
import com.next.level.solutions.calculator.fb.mp.entity.db.PhotoModelDB
import com.next.level.solutions.calculator.fb.mp.entity.db.TrashModelDB
import com.next.level.solutions.calculator.fb.mp.entity.db.VideoModelDB

@Database(
  version = 1,
  exportSchema = false,
  entities = [
    FileModelDB::class,
    NoteModelDB::class,
    PhotoModelDB::class,
    TrashModelDB::class,
    VideoModelDB::class,
    BrowserHistoryDB::class,
  ],
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class MyDatabase : RoomDatabase() {
  abstract fun hiddenFileDao(): FileDao
  abstract fun hiddenNoteDao(): NoteDao
  abstract fun hiddenPhotoDao(): PhotoDao
  abstract fun hiddenTrashDao(): TrashDao
  abstract fun hiddenVideoDao(): VideoDao
  abstract fun browserHistoryDao(): BrowserHistoryDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<MyDatabase> {
  override fun initialize(): MyDatabase
}
