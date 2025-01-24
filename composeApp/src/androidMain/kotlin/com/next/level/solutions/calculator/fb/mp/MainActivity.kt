package com.next.level.solutions.calculator.fb.mp

import android.os.Bundle
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
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory

class MainActivity : ComponentActivity() {
  companion object {
    var adsManager: AdsManager? = null
    var producePath: ((String) -> String)? = null
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    producePath = { filesDir.resolve(it).absolutePath }

    adsManager = AdsManagerImpl(
      activity = this,
      inter = AdsInterImpl(this),
      native = AdsNativeImpl(this),
      appOpen = AdsAppOpenImpl(this),
    )

    adsManager?.init {

    }

    val rootComponentFactory: (KoinFactory) -> RootComponent = {
      RootComponent(
        componentContext = defaultComponentContext(),
        navigation = it.inject(),
        factory = it,
      )
    }

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
        rootComponentFactory = rootComponentFactory,
      )
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    adsManager = null
  }
}