package com.next.level.solutions.calculator.fb.mp

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory

@Suppress("FunctionName", "unused")
fun MainViewController() = ComposeUIViewController {

  val rootComponentFactory: (KoinFactory) -> RootComponent = remember {
    {
      RootComponent(
        componentContext = DefaultComponentContext(ApplicationLifecycle()),
        navigation = it.inject(),
        factory = it,
      )
    }
  }

  App(
    rootComponentFactory = rootComponentFactory,
  )
}