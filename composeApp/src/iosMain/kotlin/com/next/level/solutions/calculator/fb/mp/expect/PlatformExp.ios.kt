package com.next.level.solutions.calculator.fb.mp.expect

import coil3.Bitmap
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusLimited
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHAuthorizationStatusRestricted
import platform.Photos.PHPhotoLibrary

actual object PlatformExp {

  actual fun currentTimeMillis(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()

  actual fun externalStoragePermissionGranted(): Boolean {
    val status = PHPhotoLibrary.authorizationStatus()
    return status == PHAuthorizationStatusAuthorized || status == PHAuthorizationStatusLimited
  }

  actual fun requestExternalStoragePermission() {
    PHPhotoLibrary.requestAuthorization { status ->
      when (status) {
        PHAuthorizationStatusAuthorized -> println("✅ Доступ к фото разрешен")
        PHAuthorizationStatusDenied, PHAuthorizationStatusRestricted -> println("⛔️ Доступ запрещен")
        PHAuthorizationStatusNotDetermined -> println("❓ Разрешение не запрашивалось")
        PHAuthorizationStatusLimited -> println("⚠️ Ограниченный доступ (iOS 14+)")
        else -> println("⚠️ Неизвестный статус")
      }
    }
  }

  actual fun systemBars(show: Boolean) {
    // TODO("Not yet implemented"):
  }

  actual fun screenOrientation(landscape: Boolean?) {
    // TODO("Not yet implemented"):
  }

  actual fun openMarket() {
    // TODO("Not yet implemented"):
  }

  actual fun shareApp() {
    // TODO("Not yet implemented"):
  }

  actual fun collapse() {
    // TODO("Not yet implemented"):
  }

  actual fun saveToCache(icon: Bitmap?, name: String, quality: Int) {
    // TODO("Not yet implemented")
  }

  actual fun shareLink(title: String?, link: String) {
    // TODO("Not yet implemented"):
  }

  actual fun openFile(fileDataUI: FileDataUI) {
    // TODO("Not yet implemented"):
  }

  actual fun setScreenOrientationListener(callback: ((Boolean) -> Unit)?) {
    // TODO("Not yet implemented"):
  }
}