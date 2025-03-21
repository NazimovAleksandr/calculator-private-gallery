package com.next.level.solutions.calculator.fb.mp.ecosystem.ads.app_open

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnPaidEventListener
import com.google.android.gms.ads.appopen.AppOpenAd
import com.next.level.solutions.calculator.fb.mp.ecosystem.analytics.AppAnalytics
import com.next.level.solutions.calculator.fb.mp.utils.NetworkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdsAppOpenImpl(
  private val activity: Activity,
  private val analytics: AppAnalytics,
  private val networkManager: NetworkManager,
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
    networkManager.runAfterNetworkConnection(::loadAd)
  }

  override fun load() {
    networkManager.runAfterNetworkConnection(::loadAd)
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
    CoroutineScope(Dispatchers.Main).launch {
      AppOpenAd.load(
        activity,
        adUnitId,
        AdRequest.Builder().build(),
        loadCallback()
      )
    }
  }

  private fun loadCallback() = object : AppOpenAd.AppOpenAdLoadCallback() {
    override fun onAdFailedToLoad(adError: LoadAdError) {
      adLoadErrorCount++

      if (adLoadErrorCount < maxAdLoadErrors) {
        networkManager.runAfterNetworkConnection(::loadAd)
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