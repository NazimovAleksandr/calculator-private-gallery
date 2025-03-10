package com.next.level.solutions.calculator.fb.mp

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import org.koin.compose.KoinApplication

@Suppress("FunctionName", "unused")
fun MainViewController() = ComposeUIViewController {
  KoinApplication(
    application = { appModules() },
    content = {
      App(
        componentContext = DefaultComponentContext(ApplicationLifecycle()),
      )
    },
  )
}