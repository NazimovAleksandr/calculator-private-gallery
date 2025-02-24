package com.next.level.solutions.calculator.fb.mp.expect

import android.os.Build
import android.os.Environment
import com.next.level.solutions.calculator.fb.mp.MainActivity

actual fun currentTimeMillis(): Long = System.currentTimeMillis()

actual fun externalStoragePermissionGranted(): Boolean {
  return when {
    Build.VERSION.SDK_INT >= 30 -> Environment.isExternalStorageManager()
    else -> MainActivity.externalStoragePermissionGranted?.invoke() ?: false
  }
}

actual fun requestExternalStoragePermission() {
  MainActivity.requestExternalStoragePermission?.invoke()
}

actual fun systemBars(show: Boolean) {
  MainActivity.systemBars?.invoke(show)
}

actual fun setAppEventListeners(callback: (AppEvent) -> Unit) {
  MainActivity.appEventListeners = callback
}

actual fun collapse() {
  MainActivity.collapse?.invoke()
}