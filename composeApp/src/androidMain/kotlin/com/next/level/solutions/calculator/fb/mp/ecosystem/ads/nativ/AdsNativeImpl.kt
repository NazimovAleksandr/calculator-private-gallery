package com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ

import android.content.Context
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.OnPaidEventListener
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import kotlinx.coroutines.flow.MutableStateFlow

class AdsNativeImpl(
  private val context: Context,
) : AdsNative, NativeAd.OnNativeAdLoadedListener, OnPaidEventListener {

  private var adLoader: AdLoader? = null
  private val ad: MutableStateFlow<NativeAd?> = MutableStateFlow(null)

  private var sdkErrorCount: Int = 0
  private var sdkErrorMaxCount: Int = 2

  private var adUnitId: String = "ca-app-pub-3940256099942544/2247696110"

  override fun onNativeAdLoaded(nativeAd: NativeAd) {
    when (adLoader?.isLoading) {
      // The AdLoader is still loading ads.
      // Expect more adLoaded or onAdFailedToLoad callbacks.
      true -> {}

      // The AdLoader has finished loading ads.
      false -> {
        sdkErrorCount = 0

        ad.value?.destroy()
        ad.value = nativeAd
        ad.value?.setOnPaidEventListener(this)
      }

      else -> {}
    }
  }

  override fun onPaidEvent(p0: AdValue) {
//    val adInfo = ad.value?.responseInfo?.loadedAdapterResponseInfo
//    val network = adInfo?.adSourceName
//    val placementId = adInfo?.adSourceId.toString()
//
//    val manualAdRevenue = ManualAdRevenue(
//      valueMicros = adValue.valueMicros,
//      currencyCode = adValue.currencyCode,
//      placementId = placementId,
//      adUnitId = adUnitId,
//      network = network.toString(),
//    )
//
//    analytics.appMetrica.reportExternalAdRevenue(AdType.NATIVE, manualAdRevenue)
//
//    analytics.ads.paidImpression(
//      adUnitId = adUnitId,
//      valueMicros = adValue.valueMicros,
//      precision = adValue.precisionType,
//      currency = adValue.currencyCode,
//      network = network,
//      type = type,
//    )
  }

  override fun init() {
    adLoader = AdLoader.Builder(context, adUnitId)
      .forNativeAd(this)
      .withAdListener(loadCallback())
      .withNativeAdOptions(NativeAdOptions.Builder().build())
      .build()

    load()
  }

  override fun isInit(): Boolean {
    return adLoader != null
  }

  override fun load() {
    if (adLoader?.isLoading == false && sdkErrorCount < sdkErrorMaxCount) {
//      analytics.ads.loading(adUnitId, type)
      adLoader?.loadAd(AdRequest.Builder().build())
    }
  }

  override fun destroy() {
    ad.value?.destroy()
    ad.value = null
  }

  override fun ad(
    size: NativeSize?,
    modifier: Modifier,
    loadAtDispose: Boolean,
    color: Color?,
    dividerSize: DividerSize,
  ): @Composable ColumnScope.() -> Unit = {
    if (isInit()) {
      NativeAdCard(
        size = size,
        modifier = modifier,
        loadAtDispose = loadAtDispose,
        loadNative = ::load,
        color = color,
        dividerSize = dividerSize,
        ad = ad,
      )
    }
  }

  private fun loadCallback() = object : AdListener() {
//    private var onAdImpressionCount = 0

//    override fun onAdClicked() {
//      super.onAdClicked()
//      analytics.ads.click(adUnitId, type)

//      when (screen) {
//        "tabs_empty" -> analytics.ads.click(adUnitId, type, "tabs")
//        else -> analytics.ads.click(adUnitId, type, screen)
//      }
//    }

//    override fun onAdFailedToLoad(adError: LoadAdError) {
//      sdkErrorCount++
//
//      if (sdkErrorCount < sdkErrorMaxCount) {
//        reload(sdkErrorCount, ::load)
//        return
//      }

//      analytics.ads.sdkError(adUnitId, adError.message, adError.code, type)
//    }

//    override fun onAdImpression() {
//      super.onAdImpression()
//      analytics.ads.impression(adUnitId, type)
//    }
  }
}