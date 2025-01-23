package com.next.level.solutions.calculator.fb.mp.ecosystem.ads.app_open

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.OnPaidEventListener
import com.google.android.gms.ads.appopen.AppOpenAd

class AdsAppOpenImpl(
  private val activity: Activity,
) : FullScreenContentCallback(), OnPaidEventListener, AdsAppOpen {

  private var ad: AppOpenAd? = null
  private var adTemp: AppOpenAd? = null
  private var callback: (() -> Unit)? = null

  private var sdkErrorCount: Int = 0
  private var sdkErrorMaxCount: Int = 2

  private var adUnitId: String = "ca-app-pub-3940256099942544/9257395921"

  private var isShown: Boolean = false

  override fun onPaidEvent(p0: AdValue) {
//    val adInfo = adTemp?.responseInfo?.loadedAdapterResponseInfo
//    val network = adInfo?.adSourceName
//    val placementId = adInfo?.adSourceId.toString()
//
//    val manualAdRevenue = ManualAdRevenue(
//      valueMicros = adValue.valueMicros,
//      currencyCode = adValue.currencyCode,
//      placementId = placementId,
//      adUnitId = adTemp?.adUnitId.toString(),
//      network = network.toString(),
//    )
//
//    analytics.appMetrica.reportExternalAdRevenue(AdType.OTHER, manualAdRevenue)
//
//    analytics.ads.paidImpression(
//      adUnitId = adUnitId,
//      valueMicros = adValue.valueMicros,
//      precision = adValue.precisionType,
//      currency = adValue.currencyCode,
//      network = network,
//      type = type,
//    )
//
//    adTemp = null
  }

//  override fun onAdClicked() {
//    super.onAdClicked()
//    analytics.ads.click(adUnitId, type)
//  }

  override fun onAdDismissedFullScreenContent() {
    isShown = false
    callback?.invoke()
    callback = null
  }

  override fun onAdFailedToShowFullScreenContent(adError: AdError) {
    isShown = false
    callback?.invoke()
    callback = null
  }

  override fun onAdShowedFullScreenContent() {
//    analytics.ads.impression(adUnitId, type)
    load()
  }

  override fun load() {
//    analytics.ads.loading(adUnitId, type)

    AppOpenAd.load(
      activity,
      adUnitId,
      AdRequest.Builder().build(),
      loadCallback()
    )
  }

  override fun state(): Boolean {
    return ad != null
  }

  override fun isShown(): Boolean {
    return isShown
  }

  override fun show(closeCallback: () -> Unit) {
//    analytics.ads.showScheduled(adUnitId, type)

    when {
      ad == null && sdkErrorCount < sdkErrorMaxCount -> {
//        analytics.ads.waitError(adUnitId, type)
      }
    }

    when {
      ad == null -> {
        closeCallback.invoke()
      }

      else -> {
        isShown = true
        callback = closeCallback
        ad?.show(activity)

        adTemp = ad
        ad = null
      }
    }
  }

  private fun loadCallback() = object : AppOpenAd.AppOpenAdLoadCallback() {
//    override fun onAdFailedToLoad(adError: LoadAdError) {
//      sdkErrorCount++
//
//      if (sdkErrorCount < sdkErrorMaxCount) {
//        reload(sdkErrorCount, ::load)
//        return
//      }
//
////      analytics.ads.sdkError(adUnitId, adError.message, adError.code, type)
//    }

    override fun onAdLoaded(appOpenAd: AppOpenAd) {
//      sdkErrorCount = 0

      ad = appOpenAd
      ad?.onPaidEventListener = this@AdsAppOpenImpl
      ad?.fullScreenContentCallback = this@AdsAppOpenImpl
    }
  }
}