package com.next.level.solutions.calculator.fb.mp.ecosystem.ads.app_open

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnPaidEventListener
import com.google.android.gms.ads.appopen.AppOpenAd
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.utils.loadAd
import com.next.level.solutions.calculator.fb.mp.ecosystem.analytics.AppAnalytics

class AdsAppOpenImpl(
  private val activity: Activity,
  private val analytics: AppAnalytics,
) : FullScreenContentCallback(), OnPaidEventListener, AdsAppOpen {

  private var ad: AppOpenAd? = null
  private var adTemp: AppOpenAd? = null
  private var callback: (() -> Unit)? = null

  private var adLoadErrorCount: Int = 0
  private var maxAdLoadErrors: Int = 2

  private var adUnitId: String = "ca-app-pub-3940256099942544/9257395921"

  private var isShown: Boolean = false

  override fun onPaidEvent(p0: AdValue) {}

  override fun onAdClicked() {}

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
    activity.loadAd(::loadAd)
  }

  override fun load() {
    activity.loadAd(::loadAd)
  }

  override fun state(): Boolean {
    return ad != null
  }

  override fun isShown(): Boolean {
    return isShown
  }

  override fun show(closeCallback: () -> Unit) {
    if (ad == null) {
      closeCallback.invoke()
      return
    }

    adTemp = ad
    isShown = true
    callback = closeCallback

    ad?.show(activity)
    ad = null
  }

  private fun loadAd() {
    AppOpenAd.load(
      activity,
      adUnitId,
      AdRequest.Builder().build(),
      loadCallback()
    )
  }

  private fun loadCallback() = object : AppOpenAd.AppOpenAdLoadCallback() {
    override fun onAdFailedToLoad(adError: LoadAdError) {
      adLoadErrorCount++

      if (adLoadErrorCount < maxAdLoadErrors) {
        activity.loadAd(::loadAd)
        return
      }
    }

    override fun onAdLoaded(appOpenAd: AppOpenAd) {
      adLoadErrorCount = 0

      ad = appOpenAd
      ad?.onPaidEventListener = this@AdsAppOpenImpl
      ad?.fullScreenContentCallback = this@AdsAppOpenImpl
    }
  }
}