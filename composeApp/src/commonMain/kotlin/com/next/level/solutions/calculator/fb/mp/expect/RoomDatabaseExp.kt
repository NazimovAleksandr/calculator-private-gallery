package com.next.level.solutions.calculator.fb.mp.expect

import androidx.room.RoomDatabase
import com.next.level.solutions.calculator.fb.mp.data.database.MyDatabase

expect fun getRoomDatabase(): RoomDatabase.Builder<MyDatabase>