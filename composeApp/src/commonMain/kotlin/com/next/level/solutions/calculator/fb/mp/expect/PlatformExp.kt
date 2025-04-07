package com.next.level.solutions.calculator.fb.mp.expect

import coil3.Bitmap
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI

@Suppress("unused")
expect object PlatformExp {
  val isDebug: Boolean
  val isIOS: Boolean
  val appVersion: String
  fun currentTimeMillis(): Long
  fun externalStoragePermissionGranted(): Boolean
  fun requestExternalStoragePermission()
  fun saveToCache(icon: Bitmap?, name: String, quality: Int = 100)
  fun shareLink(title: String?, link: String)
  fun openFile(fileDataUI: FileDataUI)
  fun systemBars(show: Boolean)
  fun screenOrientation(landscape: Boolean?)
  fun openMarket()
  fun shareApp()
  fun collapse()
  fun setScreenOrientationListener(callback: ((Boolean) -> Unit)?)
}