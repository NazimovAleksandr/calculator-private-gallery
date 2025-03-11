package com.next.level.solutions.calculator.fb.mp

import android.app.Application
import com.next.level.solutions.calculator.fb.mp.utils.Logger
import org.koin.core.error.ClosedScopeException

class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    catchClosedScopeException()
  }

  private fun catchClosedScopeException() {
    if (BuildConfig.DEBUG) {
      val handler = Thread.getDefaultUncaughtExceptionHandler()

      Thread.setDefaultUncaughtExceptionHandler { t, e ->
        when {
          e is ClosedScopeException -> Logger.e("MainApplication", "ClosedScopeException")
          else -> handler?.uncaughtException(t, e)
        }
      }
    }
  }
}