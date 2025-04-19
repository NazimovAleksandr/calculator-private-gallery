package com.next.level.solutions.calculator.fb.mp.ecosystem.analytics.app.metrica

import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AppAdRevenue

actual class AppMetrica {
  actual fun logEvent(event: String, vararg params: Pair<String, Any>) {
  }
  actual fun reportAdRevenue(type: String, revenue: AppAdRevenue) {
  }
}