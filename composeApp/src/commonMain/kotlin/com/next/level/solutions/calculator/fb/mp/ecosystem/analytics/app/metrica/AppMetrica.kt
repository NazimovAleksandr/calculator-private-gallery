package com.next.level.solutions.calculator.fb.mp.ecosystem.analytics.app.metrica

import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AppAdRevenue

expect class AppMetrica {
  fun logEvent(event: String, vararg params: Pair<String, Any>)
  fun reportAdRevenue(type: String, revenue: AppAdRevenue)
}