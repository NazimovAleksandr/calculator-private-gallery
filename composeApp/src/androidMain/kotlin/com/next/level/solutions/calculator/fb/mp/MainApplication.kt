package com.next.level.solutions.calculator.fb.mp

import android.app.Application
import com.google.firebase.remoteconfig.FirebaseRemoteConfigClientException
import com.next.level.solutions.calculator.fb.mp.utils.Logger
import org.checkerframework.checker.units.qual.t
import org.koin.core.error.ClosedScopeException

class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    catchClosedScopeException()
  }

  private fun catchClosedScopeException() {
    if (BuildConfig.DEBUG) {
      val handler = Thread.getDefaultUncaughtExceptionHandler()

      fun String.log(t: Thread, e: Throwable) {
        Logger.e("MainApplication", """
            $this:
              _1: ${e.message}
              _2: ${e.stackTraceToString()}
              _3: ${e.cause?.stackTraceToString()}
              _4: ${e.cause?.message}
              _5: ${t.name}
              _6: ${t.stackTrace}
          """.trimIndent())
      }

      Thread.setDefaultUncaughtExceptionHandler { t: Thread, e: Throwable ->
        when (e) {
          is ClosedScopeException -> "ClosedScopeException".log(t, e)
          is FirebaseRemoteConfigClientException -> "FirebaseRemoteConfigClientException".log(t, e)
          else -> handler?.uncaughtException(t, e)
        }
      }
    }
  }
}