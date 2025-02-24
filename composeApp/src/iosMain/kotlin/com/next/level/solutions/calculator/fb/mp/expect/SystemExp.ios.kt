package com.next.level.solutions.calculator.fb.mp.expect

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusDenied
import platform.Photos.PHAuthorizationStatusLimited
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHAuthorizationStatusRestricted
import platform.Photos.PHPhotoLibrary

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