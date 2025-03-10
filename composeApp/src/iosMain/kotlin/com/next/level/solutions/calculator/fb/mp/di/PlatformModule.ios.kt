package com.next.level.solutions.calculator.fb.mp.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.next.level.solutions.calculator.fb.mp.data.database.MyDatabase
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManagerImpl
import com.next.level.solutions.calculator.fb.mp.file.visibility.manager.FileVisibilityManager
import com.next.level.solutions.calculator.fb.mp.file.visibility.manager.FileVisibilityManagerImpl
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.math.parser.MathParser
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.LanguageChanger
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.LanguageChangerImpl
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSExpression
import platform.Foundation.NSFileManager
import platform.Foundation.NSNumber
import platform.Foundation.NSUserDomainMask

actual val platformModule: Module
  get() = module {

    @OptIn(ExperimentalForeignApi::class)
    single<RoomDatabase.Builder<MyDatabase>> {
      val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
      )

      val dbFilePath = requireNotNull(documentDirectory?.path) + "/my_room.db"
      Room.databaseBuilder<MyDatabase>(name = dbFilePath)
    }

    single<FileVisibilityManager> {
      FileVisibilityManagerImpl()
    }

    single<AdsManager> {
      AdsManagerImpl()
    }

    factory<MathParser> {
      object : MathParser {
        override fun calculate(expression: String): Double {
          val nsExpression = NSExpression.expressionWithFormat(expression)
          val result = nsExpression.expressionValueWithObject(null, null) as? NSNumber
          return result?.doubleValue ?: 0.0
        }
      }
    }

    factory<LanguageChanger> {
      LanguageChangerImpl()
    }
  }