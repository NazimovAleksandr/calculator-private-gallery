package com.next.level.solutions.calculator.fb.mp.utils

import platform.Foundation.NSLog

object IosAppLogger : AppLogger {
  override fun e(tag: String, message: String, throwable: Throwable?) {
    if (throwable != null) {
      NSLog("ERROR: [$tag] $message. Throwable: $throwable CAUSE ${throwable.cause}")
    } else {
      NSLog("ERROR: [$tag] $message")
    }
  }

  override fun d(tag: String, message: String) {
    NSLog("DEBUG: [$tag] $message")
  }

  override fun i(tag: String, message: String) {
    NSLog("INFO: [$tag] $message")
  }

  override fun w(tag: String, message: String) {
    NSLog("WARN: [$tag] $message")
  }
}

actual val Logger: AppLogger = IosAppLogger