package com.next.level.solutions.calculator.fb.mp.ecosystem.ads

import com.next.level.solutions.calculator.fb.mp.MainActivity

actual fun adsManager(): AdsManager {
  return MainActivity.adsManager ?: throw IllegalStateException("AdsManager not initialized")
}