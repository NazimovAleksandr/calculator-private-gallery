package com.next.level.solutions.calculator.fb.mp

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle

@Suppress("FunctionName", "unused")
fun MainViewController() = ComposeUIViewController {

  App(
    componentContext = DefaultComponentContext(ApplicationLifecycle()),
  )
}