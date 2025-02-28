package com.next.level.solutions.calculator.fb.mp.expect

import android.graphics.Bitmap
import com.next.level.solutions.calculator.fb.mp.MainActivity

actual fun currentTimeMillis(): Long = System.currentTimeMillis()

actual fun externalStoragePermissionGranted(): Boolean {
  return MainActivity.externalStoragePermissionGranted?.invoke() ?: false
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

actual fun saveToCache(icon: Bitmap?, name: String, quality: Int) {
  MainActivity.saveBitmapToCache?.invoke(icon, name, quality)
}