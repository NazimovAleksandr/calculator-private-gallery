package com.next.level.solutions.calculator.fb.mp.ecosystem.analytics

import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AppAdRevenue
import com.next.level.solutions.calculator.fb.mp.ecosystem.analytics.app.metrica.AppMetrica
import dev.gitlive.firebase.analytics.FirebaseAnalytics

class AppAnalytics(
  private val firebaseAnalytics: FirebaseAnalytics,
  private val appMetrica: AppMetrica,
) {
  fun logEvent(event: AppAnalyticsEvent, vararg params: Pair<String, Any>) {
    firebaseAnalytics.logEvent(event.value, params.toMap())
    appMetrica.logEvent(event.value, *params)
  }

  fun reportAdRevenue(type: String, appAdRevenue: AppAdRevenue) {
    appMetrica.reportAdRevenue(type, appAdRevenue)
  }
}