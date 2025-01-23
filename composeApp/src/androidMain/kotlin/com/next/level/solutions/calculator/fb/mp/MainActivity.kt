package com.next.level.solutions.calculator.fb.mp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.arkivanov.decompose.defaultComponentContext
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

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
}