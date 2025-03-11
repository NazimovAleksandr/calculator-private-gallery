package com.next.level.solutions.calculator.fb.mp.ecosystem.analytics

import dev.gitlive.firebase.analytics.FirebaseAnalytics

class AppAnalytics(
  private val firebaseAnalytics: FirebaseAnalytics,
) {
  fun logEvent(event: String, vararg params: Pair<String, Any>) {
    firebaseAnalytics.logEvent(event, paramsToMap(params.toList()))
  }

  private fun paramsToMap(list: List<Pair<String, Any>>?): Map<String, Any>? {
    list ?: return null

    return try {
      mapOf(*list.map { it.first to it.second }.toTypedArray())
    } catch (_: Exception) {
      null
    }
  }
}