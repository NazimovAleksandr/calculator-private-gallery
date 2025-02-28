package com.next.level.solutions.calculator.fb.mp.expect

import com.next.level.solutions.calculator.fb.mp.MainActivity

actual fun setAppEventListeners(callback: (AppEvent) -> Unit) {
  MainActivity.appEventListeners = callback
}