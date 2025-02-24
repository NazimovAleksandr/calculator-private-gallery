package com.next.level.solutions.calculator.fb.mp.expect

import androidx.room.Room
import androidx.room.RoomDatabase
import com.next.level.solutions.calculator.fb.mp.data.database.MyDatabase
import com.next.level.solutions.calculator.fb.mp.file.hider.FileHider
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
  val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
    directory = NSDocumentDirectory,
    inDomain = NSUserDomainMask,
    appropriateForURL = null,
    create = false,
    error = null,
  )
  return requireNotNull(documentDirectory?.path)
}

actual fun getRoomDatabase(): RoomDatabase.Builder<MyDatabase> {
  val dbFilePath = documentDirectory() + "/my_room.db"
  return Room.databaseBuilder<MyDatabase>(name = dbFilePath)
}

actual fun getFileHider(): FileHider {
  return FileHider()
}