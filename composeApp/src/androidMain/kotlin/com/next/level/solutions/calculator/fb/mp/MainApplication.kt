package com.next.level.solutions.calculator.fb.mp

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.next.level.solutions.calculator.fb.mp.expect.AppEvent
import com.next.level.solutions.calculator.fb.mp.utils.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.error.ClosedScopeException
import org.koin.core.logger.Level

class MainApplication : Application(), Application.ActivityLifecycleCallbacks {

  private var lastStartActivity: Activity? = null
  private var showAppOpenAd: Boolean = false

  private var stopJob: Job? = null
  private var destroyedJob: Job? = null

  override fun onCreate() {
    super.onCreate()
    registerActivityLifecycleCallbacks(this)
    catchClosedScopeException()
  }

  override fun onActivityStarted(activity: Activity) {
    lastStartActivity = activity

    if (showAppOpenAd && lastStartActivity is MainActivity) {
      showAppOpenAd = false

      CoroutineScope(Job()).launch(Dispatchers.Main) {
        delay(500)
        MainActivity.appEventListeners?.invoke(AppEvent.AppOpen)
      }
    }

    stopJob?.cancel()
    stopJob = null

    destroyedJob?.cancel()
    destroyedJob = null
  }

  override fun onActivityStopped(activity: Activity) {
    if (activity !is MainActivity) return

    when (lastStartActivity is MainActivity) {
      true -> lastStartActivity = null
      false -> return
    }

    stopJob?.cancel()
    stopJob = CoroutineScope(Job()).launch {
      delay(500)

      if (lastStartActivity == null) showAppOpenAd = true

      delay(500)

      if (lastStartActivity == null) {
        MainActivity.appEventListeners?.invoke(AppEvent.AppLock)
      }
    }
  }

  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
  override fun onActivityResumed(activity: Activity) {}
  override fun onActivityPaused(activity: Activity) {}
  override fun onActivityDestroyed(activity: Activity) {}
  override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

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

    startKoin {
      androidDebugLogger()
      applicationContext()
      appModules()
    }
  }

  private fun KoinApplication.androidDebugLogger() {
    androidLogger(level = if (BuildConfig.DEBUG) Level.DEBUG else Level.NONE)
  }

  private fun KoinApplication.applicationContext() {
    androidContext(androidContext = this@MainApplication)
  }
}