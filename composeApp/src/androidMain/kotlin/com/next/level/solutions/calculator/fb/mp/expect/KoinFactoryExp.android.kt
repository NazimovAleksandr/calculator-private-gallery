package com.next.level.solutions.calculator.fb.mp.expect

import androidx.room.RoomDatabase
import com.next.level.solutions.calculator.fb.mp.MainActivity
import com.next.level.solutions.calculator.fb.mp.data.database.MyDatabase
import com.next.level.solutions.calculator.fb.mp.file.hider.FileHider

actual fun getRoomDatabase(): RoomDatabase.Builder<MyDatabase> {
  return MainActivity.roomDatabase?.invoke() ?: throw IllegalStateException("RoomDatabase not initialized")
}

actual fun getFileHider(): FileHider {
  return MainActivity.fileHider?.invoke() ?: throw IllegalStateException("FileHider not initialized")
}