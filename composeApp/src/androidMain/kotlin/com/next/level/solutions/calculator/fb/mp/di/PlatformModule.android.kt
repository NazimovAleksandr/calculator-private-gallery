package com.next.level.solutions.calculator.fb.mp.di

import android.app.Activity
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.next.level.solutions.calculator.fb.mp.data.database.MyDatabase
import com.next.level.solutions.calculator.fb.mp.data.datastore.produce.path.ProducePath
import com.next.level.solutions.calculator.fb.mp.data.datastore.produce.path.ProducePathImpl
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManagerImpl
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.app_open.AdsAppOpen
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.app_open.AdsAppOpenImpl
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.inter.AdsInter
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.inter.AdsInterImpl
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.AdsNative
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.AdsNativeImpl
import com.next.level.solutions.calculator.fb.mp.ecosystem.analytics.AppAnalytics
import com.next.level.solutions.calculator.fb.mp.expect.AppEventListener
import com.next.level.solutions.calculator.fb.mp.file.visibility.manager.FileVisibilityManager
import com.next.level.solutions.calculator.fb.mp.file.visibility.manager.FileVisibilityManagerImpl
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.math.parser.MathParser
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.math.parser.MathParserImpl
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.ChangerLocalStore
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.LanguageChanger
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.LanguageChangerImpl
import com.next.level.solutions.calculator.fb.mp.utils.NetworkManager
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.mariuszgromada.math.mxparser.Expression

actual val platformModule: Module
  get() = module {
    singleOf<FileVisibilityManager, Context>(::FileVisibilityManagerImpl)
    singleOf<LanguageChanger, Context, ChangerLocalStore>(::LanguageChangerImpl)
    singleOf<ProducePath, Context>(::ProducePathImpl)

    singleOf<AdsManager, AdsInter, AdsNative, AdsAppOpen, Activity, NetworkManager>(::AdsManagerImpl)
    singleOf<AdsInter, Activity, AppAnalytics, NetworkManager>(::AdsInterImpl)
    singleOf<AdsAppOpen, Activity, AppAnalytics, NetworkManager>(::AdsAppOpenImpl)
    singleOf<AdsNative, Context, AppAnalytics, NetworkManager>(::AdsNativeImpl)

    singleOf(::AppEventListener)

    single<RoomDatabase.Builder<MyDatabase>> {
      val applicationContext = get<Context>().applicationContext
      val dbFile = applicationContext.getDatabasePath("my_room.db")

      Room.databaseBuilder<MyDatabase>(
        context = applicationContext,
        name = dbFile.absolutePath,
      )
    }

    single<Activity> {
      get<Context>() as Activity
    }

    factoryOf(::NetworkManager)
    factoryOf<MathParser>(::MathParserImpl)

    factory {
      ChangerLocalStore(get<Context>().getSharedPreferences("Changer", Context.MODE_PRIVATE))
    }
  }