package com.next.level.solutions.calculator.fb.mp.utils

interface AppLogger {
  fun e(tag: String, message: String, throwable: Throwable? = null)
  fun d(tag: String, message: String)
  fun i(tag: String, message: String)
  fun w(tag: String, message: String)
}

expect val Logger: AppLogger