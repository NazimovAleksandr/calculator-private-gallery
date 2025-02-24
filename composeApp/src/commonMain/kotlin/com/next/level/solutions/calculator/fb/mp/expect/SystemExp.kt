package com.next.level.solutions.calculator.fb.mp.expect

expect fun currentTimeMillis(): Long
expect fun externalStoragePermissionGranted(): Boolean
expect fun requestExternalStoragePermission()
expect fun systemBars(show: Boolean)
expect fun collapse()

sealed interface AppEvent{
  object AppOpen: AppEvent
  object AppLock: AppEvent
}

expect fun setAppEventListeners(callback: (AppEvent) -> Unit)