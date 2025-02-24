package com.next.level.solutions.calculator.fb.mp.di

import androidx.room.RoomDatabase
import com.next.level.solutions.calculator.fb.mp.data.database.MyDatabase
import com.next.level.solutions.calculator.fb.mp.expect.getFileHider
import com.next.level.solutions.calculator.fb.mp.expect.getRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val dataModule = module {
  single<MyDatabase> {
    val databaseBuilder: RoomDatabase.Builder<MyDatabase> = getRoomDatabase()

    databaseBuilder
//      .addMigrations(MyDatabase.MIGRATIONS)
      .setQueryCoroutineContext(Dispatchers.IO)
      .build()
  }

  single { getFileHider() }
}