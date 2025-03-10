package com.next.level.solutions.calculator.fb.mp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.next.level.solutions.calculator.fb.mp.MainActivity
import com.next.level.solutions.calculator.fb.mp.data.database.MyDatabase
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.file.visibility.manager.FileVisibilityManager
import com.next.level.solutions.calculator.fb.mp.file.visibility.manager.FileVisibilityManagerImpl
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.math.parser.MathParser
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.ChangerLocalStore
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.LanguageChanger
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.LanguageChangerImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.mariuszgromada.math.mxparser.Expression

actual val platformModule: Module
  get() = module {
    single<RoomDatabase.Builder<MyDatabase>> {
      val applicationContext = get<Context>().applicationContext
      val dbFile = applicationContext.getDatabasePath("my_room.db")

      Room.databaseBuilder<MyDatabase>(
        context = applicationContext,
        name = dbFile.absolutePath,
      )
    }

    singleOf<FileVisibilityManager, Context>(::FileVisibilityManagerImpl)

    single<AdsManager> {
      MainActivity.adsManager ?: throw IllegalStateException("AdsManager not initialized")
    }

    factory<MathParser> {
      object : MathParser {
        override fun calculate(expression: String): Double {
          return Expression(expression).calculate()
        }
      }
    }

    factoryOf<LanguageChanger, Context, ChangerLocalStore>(::LanguageChangerImpl)

    factory {
      ChangerLocalStore(get<Context>().getSharedPreferences("Changer", Context.MODE_PRIVATE))
    }
  }