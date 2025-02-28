package com.next.level.solutions.calculator.fb.mp.expect

import android.graphics.Bitmap
import com.next.level.solutions.calculator.fb.mp.MainActivity
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI

actual object PlatformExp {
  actual fun currentTimeMillis(): Long = System.currentTimeMillis()

  actual fun externalStoragePermissionGranted(): Boolean {
    return MainActivity.expect?.value?.externalStoragePermissionGranted?.invoke() ?: false
  }

  actual fun requestExternalStoragePermission() {
    MainActivity.expect?.value?.requestExternalStoragePermission?.invoke()
  }

  actual fun systemBars(show: Boolean) {
    MainActivity.expect?.value?.systemBars?.invoke(show)
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

  actual fun toast(text: String) {
    MainActivity.expect?.value?.toast?.invoke(text)
  }

  actual fun openFile(fileDataUI: FileDataUI) {
    MainActivity.expect?.value?.openFile?.invoke(fileDataUI)
  }
}