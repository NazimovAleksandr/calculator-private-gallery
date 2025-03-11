package com.next.level.solutions.calculator.fb.mp

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.ApplicationLifecycle
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.crashlytics.crashlytics
import dev.gitlive.firebase.initialize
import org.koin.compose.KoinApplication

@Suppress("FunctionName", "unused")
fun MainViewController() = ComposeUIViewController {
  Firebase.initialize()
  Firebase.crashlytics.setCrashlyticsCollectionEnabled(true)

  KoinApplication(
    application = { appModules() },
    content = {
      App(
        componentContext = DefaultComponentContext(ApplicationLifecycle()),
      )
    },
  )
}