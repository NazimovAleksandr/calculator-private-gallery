package com.next.level.solutions.calculator.fb.mp.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.next.level.solutions.calculator.fb.mp.data.database.MyDatabase
import com.next.level.solutions.calculator.fb.mp.data.datastore.local.store.LocalStore
import com.next.level.solutions.calculator.fb.mp.data.datastore.local.store.LocalStoreImpl
import com.next.level.solutions.calculator.fb.mp.data.datastore.produce.path.ProducePath
import com.next.level.solutions.calculator.fb.mp.data.datastore.produce.path.ProducePathImpl
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManagerImpl
import com.next.level.solutions.calculator.fb.mp.ecosystem.analytics.app.metrica.AppMetrica
import com.next.level.solutions.calculator.fb.mp.expect.AppEventListener
import com.next.level.solutions.calculator.fb.mp.expect.AppUpdate
import com.next.level.solutions.calculator.fb.mp.file.visibility.manager.FileVisibilityManager
import com.next.level.solutions.calculator.fb.mp.file.visibility.manager.FileVisibilityManagerImpl
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.math.parser.MathParser
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.LanguageChanger
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.LanguageChangerImpl
import com.next.level.solutions.calculator.fb.mp.utils.NetworkManager
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSExpression
import platform.Foundation.NSFileManager
import platform.Foundation.NSNumber
import platform.Foundation.NSUserDomainMask

actual val platformModule: Module
  get() = module {
    singleOf<FileVisibilityManager>(::FileVisibilityManagerImpl)
    singleOf<LanguageChanger>(::LanguageChangerImpl)
    singleOf<ProducePath>(::ProducePathImpl)
    singleOf<LocalStore>(::LocalStoreImpl)

    singleOf<AdsManager>(::AdsManagerImpl)

    singleOf(::AppEventListener)
    singleOf(::AppMetrica)
    singleOf(::AppUpdate)

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

    factoryOf(::NetworkManager)

    factory<MathParser> {
      object : MathParser {
        override fun calculate(expression: String): Double {
          val nsExpression = NSExpression.expressionWithFormat(expression)
          val result = nsExpression.expressionValueWithObject(null, null) as? NSNumber
          return result?.doubleValue ?: 0.0
        }
      }
    }
  }