package com.next.level.solutions.calculator.fb.mp.expect

import coil3.Bitmap

expect fun currentTimeMillis(): Long
expect fun externalStoragePermissionGranted(): Boolean
expect fun requestExternalStoragePermission()
expect fun systemBars(show: Boolean)
expect fun collapse()
expect fun saveToCache(icon: Bitmap?, name: String, quality: Int = 100)

sealed interface AppEvent{
  object AppOpen: AppEvent
  object AppLock: AppEvent
}

expect fun setAppEventListeners(callback: (AppEvent) -> Unit)