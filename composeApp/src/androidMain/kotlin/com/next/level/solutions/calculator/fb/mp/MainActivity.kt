package com.next.level.solutions.calculator.fb.mp

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.arkivanov.decompose.defaultComponentContext
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManagerImpl
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.app_open.AdsAppOpenImpl
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.inter.AdsInterImpl
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.AdsNativeImpl
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.ChangerLocalStore
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer.LanguageChangerImpl

class MainActivity : ComponentActivity() {
  companion object {
    var adsManager: AdsManager? = null
    var languageChanger: Lazy<LanguageChangerImpl>? = null
    var producePath: ((String) -> String)? = null
    var roomDatabase: (() -> RoomDatabase.Builder<MyDatabase>)? = null
  }

  override fun attachBaseContext(newBase: Context?) {
    val newBaseContext = newBase?.let { context ->
      val resources = context.resources
      val configuration = resources.configuration

      val store = ChangerLocalStore(context.getSharedPreferences("Changer", Context.MODE_PRIVATE))
      val localeToSwitchTo = store.getLocale()

      configuration.setLocale(localeToSwitchTo)
      val localeList = LocaleList(localeToSwitchTo)
      LocaleList.setDefault(localeList)
      configuration.setLocales(localeList)

      ContextWrapper(context.createConfigurationContext(configuration))
    }

    super.attachBaseContext(newBaseContext)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    producePath = { filesDir.resolve(it).absolutePath }

    languageChanger = lazy {
      LanguageChangerImpl(
        activity = this,
        store = ChangerLocalStore(getSharedPreferences("Changer", Context.MODE_PRIVATE))
      )
    }

    roomDatabase = {
      val dbFile = applicationContext.getDatabasePath("my_room.db")

      Room.databaseBuilder<MyDatabase>(
        context = applicationContext,
        name = dbFile.absolutePath
      )
    }

    adsManager = AdsManagerImpl(
      activity = this,
      inter = AdsInterImpl(this),
      native = AdsNativeImpl(this),
      appOpen = AdsAppOpenImpl(this),
    )

    adsManager?.init {

    }

    val componentContext = defaultComponentContext()

    val color = when (true) {
      true -> Color.Black.copy(alpha = 0.01f)
      else -> Color.White.copy(alpha = 0.01f)
    }.toArgb()

    enableEdgeToEdge(
      statusBarStyle = SystemBarStyle.auto(lightScrim = color, darkScrim = color),
      navigationBarStyle = SystemBarStyle.auto(lightScrim = color, darkScrim = color),
    )

    setContent {
      App(
        componentContext = componentContext,
      )
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    adsManager = null
  }
}