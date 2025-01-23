package com.next.level.solutions.calculator.fb.mp.utils

import android.util.Log

object AndroidAppLogger: AppLogger {
  override fun e(tag: String, message: String, throwable: Throwable?) {
    if (throwable != null) {
      Log.e(tag, message, throwable)
    } else {
      Log.e(tag, message)
    }
  }

  override fun d(tag: String, message: String) {
    Log.d(tag, message)
  }

  override fun i(tag: String, message: String) {
    Log.i(tag, message)
  }

  override fun w(tag: String, message: String) {
    Log.w(tag, message)
  }
}

actual val Logger: AppLogger  = AndroidAppLogger