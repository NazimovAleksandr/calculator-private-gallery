package com.next.level.solutions.calculator.fb.mp.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.next.level.solutions.calculator.fb.mp.data.database.AppDatabase
import com.next.level.solutions.calculator.fb.mp.data.database.MyDatabase
import com.next.level.solutions.calculator.fb.mp.data.datastore.AppDatastore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule: Module = module {
  single<MyDatabase> {
    val databaseBuilder: RoomDatabase.Builder<MyDatabase> = get()

    databaseBuilder
      .setDriver(BundledSQLiteDriver())
      .setQueryCoroutineContext(Dispatchers.IO)
      .build()
  }

  singleOf(:: AppDatabase)
  singleOf(::AppDatastore)
}