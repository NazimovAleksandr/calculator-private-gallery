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
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnPaidEventListener
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.next.level.solutions.calculator.fb.mp.BuildConfig
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AppAdRevenue
import com.next.level.solutions.calculator.fb.mp.ecosystem.analytics.AppAnalytics
import com.next.level.solutions.calculator.fb.mp.ecosystem.analytics.AppAnalyticsEvent
import com.next.level.solutions.calculator.fb.mp.utils.Logger
import com.next.level.solutions.calculator.fb.mp.utils.NetworkManager
import io.appmetrica.analytics.AdType
import kotlinx.coroutines.flow.MutableStateFlow

class AdsNativeImpl(
  private val context: Context,
  private val analytics: AppAnalytics,
  private val networkManager: NetworkManager,
) : AdsNative, NativeAd.OnNativeAdLoadedListener, OnPaidEventListener {

  private var adLoader: AdLoader? = null
  private val ad: MutableStateFlow<NativeAd?> = MutableStateFlow(null)

  private var adLoadErrorCount: Int = 0
  private var maxAdLoadErrors: Int = 2

  private var adUnitId: String = BuildConfig.ADMOB_NATIVE_ID

  @Suppress("PrivatePropertyName")
  private val TAG = this::class.simpleName.toString()

  override fun onNativeAdLoaded(nativeAd: NativeAd) {
    if (adLoader?.isLoading != false) return
    Logger.d(TAG, "onNativeAdLoaded")

    adLoadErrorCount = 0

    ad.value?.destroy()
    ad.value = nativeAd
    ad.value?.setOnPaidEventListener(this)
  }

  override fun onPaidEvent(adValue: AdValue) {
    Logger.d(TAG, "onPaidEvent")

    val nativeAd = ad.value
    val adInfo = nativeAd?.responseInfo?.loadedAdapterResponseInfo
    val network = adInfo?.adSourceName
    val placementId = adInfo?.adSourceId.toString()

    val appAdRevenue = AppAdRevenue(
      valueMicros = adValue.valueMicros,
      currencyCode = adValue.currencyCode,
      placementId = placementId,
      adUnitId = adUnitId,
      network = network.toString(),
    )

    analytics.logEvent(AppAnalyticsEvent.Paid, "type" to AdType.NATIVE.name)
    analytics.reportAdRevenue(AdType.NATIVE.name, appAdRevenue)
  }

  override fun init() {
    adLoader = AdLoader.Builder(context, adUnitId)
      .forNativeAd(this)
      .withAdListener(loadCallback())
      .withNativeAdOptions(NativeAdOptions.Builder().build())
      .build()

    networkManager.runAfterNetworkConnection(::loadAd)
  }

  override fun isInit(): Boolean {
    return adLoader != null
  }

  override fun load() {
    networkManager.runAfterNetworkConnection(::loadAd)
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

  private fun loadAd() {
    if (adLoader?.isLoading == false && adLoadErrorCount < maxAdLoadErrors) {
      adLoader?.loadAd(AdRequest.Builder().build())
    }
  }

  private fun loadCallback() = object : AdListener() {
    override fun onAdFailedToLoad(adError: LoadAdError) {
      Logger.e(TAG, "onAdFailedToLoad: ${adError.message}")

      adLoadErrorCount++

      if (adLoadErrorCount < maxAdLoadErrors) {
        networkManager.runAfterNetworkConnection(::loadAd)
        return
      }
    }

    override fun onAdImpression() {
      Logger.d(TAG, "onAdImpression")
    }

    override fun onAdClicked() {
      Logger.d(TAG, "onAdClicked")
    }
  }
}