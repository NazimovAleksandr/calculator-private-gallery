package com.next.level.solutions.calculator.fb.mp.ecosystem.ads.inter

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.OnPaidEventListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AdsInterImpl(
  private val activity: Activity,
) : FullScreenContentCallback(), OnPaidEventListener, AdsInter {

  private var ad: InterstitialAd? = null
  private var adTemp: InterstitialAd? = null
  private var callback: (() -> Unit)? = null

  private var sdkErrorCount: Int = 0
  private var sdkErrorMaxCount: Int = 2

  private var adUnitId: String = "ca-app-pub-3940256099942544/1033173712"

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
//    analytics.appMetrica.reportExternalAdRevenue(AdType.INTERSTITIAL, manualAdRevenue)
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
    callback?.invoke()
    callback = null
  }

  override fun onAdFailedToShowFullScreenContent(adError: AdError) {
    callback?.invoke()
    callback = null
  }

  override fun onAdShowedFullScreenContent() {
//    analytics.ads.impression(adUnitId, type)
    load()
  }

  override fun load() {
//    analytics.ads.loading(adUnitId, type)

    InterstitialAd.load(
      activity,
      adUnitId,
      AdRequest.Builder().build(),
      loadCallback()
    )
  }

  override fun state(): Boolean {
    return ad != null
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
        callback = closeCallback
        ad?.show(activity)

        adTemp = ad
        ad = null
      }
    }
  }

  private fun loadCallback() = object : InterstitialAdLoadCallback() {
//    override fun onAdFailedToLoad(adError: LoadAdError) {
//      sdkErrorCount++
//
//      if (sdkErrorCount < sdkErrorMaxCount) {
//        reload(sdkErrorCount, ::load)
//        return
//      }
//
//      analytics.ads.sdkError(adUnitId, adError.message, adError.code, type)
//    }

    override fun onAdLoaded(interAd: InterstitialAd) {
      sdkErrorCount = 0

      ad = interAd
      ad?.onPaidEventListener = this@AdsInterImpl
      ad?.fullScreenContentCallback = this@AdsInterImpl
    }
  }
}