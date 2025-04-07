package com.next.level.solutions.calculator.fb.mp.expect

import android.graphics.Bitmap
import com.next.level.solutions.calculator.fb.mp.BuildConfig
import com.next.level.solutions.calculator.fb.mp.MainActivity
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI

actual object PlatformExp {
  actual val isDebug: Boolean get() = BuildConfig.DEBUG
  actual val isIOS: Boolean get() = false
  actual val appVersion: String get() = BuildConfig.VERSION_NAME

  actual fun currentTimeMillis(): Long = System.currentTimeMillis()

  actual fun externalStoragePermissionGranted(): Boolean {
    return MainActivity.expect?.value?.externalStoragePermissionGranted?.invoke() == true
  }

  actual fun requestExternalStoragePermission() {
    MainActivity.expect?.value?.requestExternalStoragePermission?.invoke()
  }

  actual fun systemBars(show: Boolean) {
    MainActivity.expect?.value?.systemBars?.invoke(show)
  }

  actual fun screenOrientation(landscape: Boolean?) {
    when (landscape) {
      null -> MainActivity.expect?.value?.screenOrientationFullSensor?.invoke()
      true -> MainActivity.expect?.value?.screenOrientationSensorLandscape?.invoke()
      else -> MainActivity.expect?.value?.screenOrientationPortrait?.invoke()
    }
  }

  actual fun collapse() {
    MainActivity.expect?.value?.collapse?.invoke()
  }

  actual fun openMarket() {
    MainActivity.expect?.value?.openMarket?.invoke()
  }

  actual fun shareApp() {
    MainActivity.expect?.value?.shareApp?.invoke()
  }

  actual fun saveToCache(icon: Bitmap?, name: String, quality: Int) {
    MainActivity.expect?.value?.saveBitmapToCache?.invoke(icon, name, quality)
  }

  actual fun shareLink(title: String?, link: String) {
    MainActivity.expect?.value?.shareLink?.invoke(title, link)
  }

  actual fun openFile(fileDataUI: FileDataUI) {
    MainActivity.expect?.value?.openFile?.invoke(fileDataUI)
  }

  actual fun setScreenOrientationListener(callback: ((Boolean) -> Unit)?) {
    MainActivity.expect?.value?.screenOrientationListener = callback
  }
}