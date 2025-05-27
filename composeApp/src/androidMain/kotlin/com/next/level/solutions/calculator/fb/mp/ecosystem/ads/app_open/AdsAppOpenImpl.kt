package com.next.level.solutions.calculator.fb.mp.ecosystem.ads.app_open

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnPaidEventListener
import com.google.android.gms.ads.appopen.AppOpenAd
import com.next.level.solutions.calculator.fb.mp.BuildConfig
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AppAdRevenue
import com.next.level.solutions.calculator.fb.mp.ecosystem.analytics.AppAnalytics
import com.next.level.solutions.calculator.fb.mp.ecosystem.analytics.AppAnalyticsEvent
import com.next.level.solutions.calculator.fb.mp.utils.Logger
import com.next.level.solutions.calculator.fb.mp.utils.NetworkManager
import dev.gitlive.firebase.analytics.FirebaseAnalyticsParam
import io.appmetrica.analytics.AdType
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

  private var adUnitId: String = BuildConfig.ADMOB_APP_OPEN_ID

  @Suppress("PrivatePropertyName")
  private val TAG = this::class.simpleName.toString()

  private var isShown: Boolean = false

  override fun onPaidEvent(adValue: AdValue) {
    Logger.d(TAG, "onPaidEvent")

    val adInfo = adTemp?.responseInfo?.loadedAdapterResponseInfo
    val network = adInfo?.adSourceName
    val placementId = adInfo?.adSourceId.toString()

    val appAdRevenue = AppAdRevenue(
      valueMicros = adValue.valueMicros,
      currencyCode = adValue.currencyCode,
      placementId = placementId,
      adUnitId = adUnitId,
      network = network.toString(),
    )

    analytics.logEvent(
      AppAnalyticsEvent.Paid,
      FirebaseAnalyticsParam.PAYMENT_TYPE to AdType.APP_OPEN.name,
    )
    analytics.reportAdRevenue(AdType.APP_OPEN.name, appAdRevenue)
  }

  override fun onAdClicked() {
    Logger.d(TAG, "onAdClicked")
  }

  override fun onAdDismissedFullScreenContent() {
    Logger.d(TAG, "onAdDismissedFullScreenContent")

    isShown = false
    callback?.invoke()
    callback = null
  }

  override fun onAdFailedToShowFullScreenContent(adError: AdError) {
    Logger.d(TAG, "onAdFailedToShowFullScreenContent")

    isShown = false
    callback?.invoke()
    callback = null
  }

  override fun onAdShowedFullScreenContent() {
    Logger.d(TAG, "onAdShowedFullScreenContent")

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
      Logger.e(TAG, "onAdFailedToLoad: ${adError.message}")

      adLoadErrorCount++

      if (adLoadErrorCount < maxAdLoadErrors) {
        networkManager.runAfterNetworkConnection(::loadAd)
        return
      }
    }

    override fun onAdLoaded(appOpenAd: AppOpenAd) {
      Logger.d(TAG, "onAdLoaded")

      adLoadErrorCount = 0

      ad = appOpenAd
      ad?.onPaidEventListener = this@AdsAppOpenImpl
      ad?.fullScreenContentCallback = this@AdsAppOpenImpl
    }
  }
}