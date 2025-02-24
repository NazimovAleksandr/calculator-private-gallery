package com.next.level.solutions.calculator.fb.mp.expect

import androidx.room.RoomDatabase
import com.next.level.solutions.calculator.fb.mp.data.database.MyDatabase
import com.next.level.solutions.calculator.fb.mp.file.hider.FileHider

expect fun getFileHider(): FileHider
expect fun getRoomDatabase(): RoomDatabase.Builder<MyDatabase>