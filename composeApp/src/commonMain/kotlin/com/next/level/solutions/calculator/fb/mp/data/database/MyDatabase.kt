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

internal expect object AppDatabaseCtor : RoomDatabaseConstructor<MyDatabase>

@Database(
  version = 1,
  exportSchema = false,
  entities = [
    BrowserHistoryDB::class,
    FileModelDB::class,
    NoteModelDB::class,
    PhotoModelDB::class,
    TrashModelDB::class,
    VideoModelDB::class,
  ],
)
@ConstructedBy(AppDatabaseCtor::class)
abstract class MyDatabase : RoomDatabase() {
  abstract fun browserHistoryDao(): BrowserHistoryDao
  abstract fun fileDao(): FileDao
  abstract fun noteDao(): NoteDao
  abstract fun photoDao(): PhotoDao
  abstract fun trashDao(): TrashDao
  abstract fun videoDao(): VideoDao
}
