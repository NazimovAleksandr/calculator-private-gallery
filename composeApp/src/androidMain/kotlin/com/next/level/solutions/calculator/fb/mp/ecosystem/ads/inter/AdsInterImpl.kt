package com.next.level.solutions.calculator.fb.mp.ecosystem.ads.inter

import android.app.Activity
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnPaidEventListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
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

  private var adUnitId: String = BuildConfig.ADMOB_INTER_ID

  private val state = mutableStateOf(false)

  @Suppress("PrivatePropertyName")
  private val TAG = this::class.simpleName.toString()

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
      AppAnalyticsEvent.AdPaid,
      FirebaseAnalyticsParam.AD_FORMAT to AdType.INTERSTITIAL.name,
    )
    analytics.reportAdRevenue(AdType.INTERSTITIAL.name, appAdRevenue)
  }

  override fun onAdClicked() {
    Logger.d(TAG, "onAdClicked")
  }

  override fun onAdDismissedFullScreenContent() {
    Logger.d(TAG, "onAdDismissedFullScreenContent")

    callback?.invoke()
    callback = null
  }

  override fun onAdFailedToShowFullScreenContent(adError: AdError) {
    Logger.d(TAG, "onAdFailedToShowFullScreenContent")

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

  override fun state(): State<Boolean> {
    return state
  }

  override fun show(closeCallback: () -> Unit) {
    if (ad == null) {
      closeCallback.invoke()
      return
    }

    adTemp = ad
    callback = closeCallback

    state.value = false
    ad?.show(activity)
    ad = null
  }

  private fun loadAd() {
    CoroutineScope(Dispatchers.Main).launch {
      InterstitialAd.load(
        /* context = */ activity,
        /* adUnitId = */ adUnitId,
        /* adRequest = */ AdRequest.Builder().build(),
        /* loadCallback = */ adLoadListener()
      )
    }
  }

  private fun adLoadListener() = object : InterstitialAdLoadCallback() {
    override fun onAdFailedToLoad(adError: LoadAdError) {
      Logger.e(TAG, "onAdFailedToLoad: ${adError.message}")

      analytics.logEvent(
        AppAnalyticsEvent.AdLoadError,
        FirebaseAnalyticsParam.AD_FORMAT to AdType.INTERSTITIAL.name,
        FirebaseAnalyticsParam.CONTENT_TYPE to adError.code,
        FirebaseAnalyticsParam.CONTENT to adError.message,
      )

      adLoadErrorCount++

      if (adLoadErrorCount < maxAdLoadErrors) {
        networkManager.runAfterNetworkConnection(::loadAd)
        return
      }
    }

    override fun onAdLoaded(interAd: InterstitialAd) {
      Logger.d(TAG, "onAdLoaded")

      adLoadErrorCount = 0

      state.value = true

      ad = interAd
      ad?.onPaidEventListener = this@AdsInterImpl
      ad?.fullScreenContentCallback = this@AdsInterImpl
    }
  }
}