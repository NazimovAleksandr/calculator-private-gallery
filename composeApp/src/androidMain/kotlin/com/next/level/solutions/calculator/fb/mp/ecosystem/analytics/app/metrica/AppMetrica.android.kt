package com.next.level.solutions.calculator.fb.mp.ecosystem.analytics.app.metrica

import android.content.Context
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AppAdRevenue
import io.appmetrica.analytics.AdRevenue
import io.appmetrica.analytics.AdType
import io.appmetrica.analytics.AppMetrica
import java.math.BigDecimal
import java.util.Currency

actual class AppMetrica(
  context: Context,
) {
  private val demo = context.packageName.contains("demo")

  actual fun logEvent(event: String, vararg params: Pair<String, Any>) {
    AppMetrica.reportEvent(event, params.toMap())
  }

  actual fun reportAdRevenue(
    type: String,
    revenue: AppAdRevenue,
  ) {
    val adType = when (type) {
      "MREC" -> AdType.MREC
      "NATIVE" -> AdType.NATIVE
      "BANNER" -> AdType.BANNER
      "REWARDED" -> AdType.REWARDED
      "APP_OPEN" -> AdType.APP_OPEN
      "INTERSTITIAL" -> AdType.INTERSTITIAL
      else -> AdType.OTHER
    }

    val adRevenue: AdRevenue = when {
      demo -> AdRevenue.newBuilder(BigDecimal("0.0001"), Currency.getInstance("USD"))
      else -> AdRevenue.newBuilder(revenue.valueMicros, Currency.getInstance(revenue.currencyCode))
    }
      .withAdType(adType)
      .withAdUnitId(revenue.adUnitId)
      .withAdNetwork(revenue.network)
      .withAdPlacementId(revenue.placementId)
      .build()

    AppMetrica.reportAdRevenue(adRevenue)
  }
}