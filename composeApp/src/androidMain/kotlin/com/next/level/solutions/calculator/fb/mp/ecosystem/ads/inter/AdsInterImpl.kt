package com.next.level.solutions.calculator.fb.mp.ecosystem.ads.inter

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnPaidEventListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.next.level.solutions.calculator.fb.mp.ecosystem.analytics.AppAnalytics
import com.next.level.solutions.calculator.fb.mp.utils.NetworkManager

class AdsInterImpl(
  private val activity: Activity,
  private val analytics: AppAnalytics,
  private val networkManager: NetworkManager,
) : FullScreenContentCallback(), OnPaidEventListener, AdsInter {

  private var ad: InterstitialAd? = null
  private var adTemp: InterstitialAd? = null
  private var callback: (() -> Unit)? = null

  private var adLoadErrorCount: Int = 0
  private var maxAdLoadErrors: Int = 2

  private var adUnitId: String = "ca-app-pub-3940256099942544/1033173712"

  override fun onPaidEvent(p0: AdValue) {}

  override fun onAdClicked() {}

  override fun onAdDismissedFullScreenContent() {
    callback?.invoke()
    callback = null
  }

  override fun onAdFailedToShowFullScreenContent(adError: AdError) {
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

  override fun show(closeCallback: () -> Unit) {

    if (ad == null) {
      closeCallback.invoke()
      return
    }

    adTemp = ad
    callback = closeCallback

    ad?.show(activity)
    ad = null
  }

  private fun loadAd() {
    InterstitialAd.load(
      /* context = */ activity,
      /* adUnitId = */ adUnitId,
      /* adRequest = */ AdRequest.Builder().build(),
      /* loadCallback = */ adLoadListener()
    )
  }

  private fun adLoadListener() = object : InterstitialAdLoadCallback() {
    override fun onAdFailedToLoad(adError: LoadAdError) {
      adLoadErrorCount++

      if (adLoadErrorCount < maxAdLoadErrors) {
        networkManager.runAfterNetworkConnection(::loadAd)
        return
      }
    }

    override fun onAdLoaded(interAd: InterstitialAd) {
      adLoadErrorCount = 0

      ad = interAd
      ad?.onPaidEventListener = this@AdsInterImpl
      ad?.fullScreenContentCallback = this@AdsInterImpl
    }
  }
}