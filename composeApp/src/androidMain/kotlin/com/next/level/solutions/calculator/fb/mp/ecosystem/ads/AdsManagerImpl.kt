package com.next.level.solutions.calculator.fb.mp.ecosystem.ads

import android.app.Activity
import android.telephony.TelephonyManager
import androidx.core.content.getSystemService
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.FormError
import com.google.android.ump.UserMessagingPlatform.getConsentInformation
import com.google.android.ump.UserMessagingPlatform.loadAndShowConsentFormIfRequired
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.app_open.AdsAppOpen
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.inter.AdsInter
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.AdsNative
import com.next.level.solutions.calculator.fb.mp.utils.NetworkManager

class AdsManagerImpl(
  override val inter: AdsInter,
  override val native: AdsNative,
  override val appOpen: AdsAppOpen,
  private val activity: Activity,
  private val networkManager: NetworkManager,
) : AdsManager {
  private var init: Boolean = false

  override fun init(onComplete: () -> Unit) {
    if (init) {
//      analytics

//      appOpen.reload()
//      inter.reload()
//      native.reload()

      return
    }

    val isUS = activity.getSystemService<TelephonyManager>()
      ?.networkCountryIso
      .equals("us", true)

    if (isUS) {
      onComplete.invoke()
      return
    }

    networkManager.runAfterNetworkConnection {
      initAds(onComplete)
    }

    init = true
  }

  private fun initAds(onComplete: () -> Unit) {
    consentInfoUpdate(activity) {
//      mintegral(activity)
//      appLovin(activity)

      val initializationComplete: (InitializationStatus) -> Unit = {
        onComplete.invoke()

//        unity3d(activity)
//        vungle()

//        inter.load()
//        native.init()
//        appOpen.load()
      }

      MobileAds.initialize(
        /* context = */ activity.application,
        /* listener = */ initializationComplete,
      )
    }
  }

  private fun consentInfoUpdate(
    activity: Activity,
    success: () -> Unit,
  ) {
    val consent: ConsentInformation = getConsentInformation(activity)
    val params: ConsentRequestParameters = ConsentRequestParameters.Builder().build()

    val checkConsentStatus: (FormError?) -> Unit = { if (consent.canRequestAds()) success() }
    val load: () -> Unit = { loadAndShowConsentFormIfRequired(activity, checkConsentStatus) }

    consent.requestConsentInfoUpdate(activity, params, load, checkConsentStatus)
  }

//  private fun mintegral(activity: Activity): Unit? = MBridgeSDKFactory.getMBridgeSDK()?.setConsentStatus(activity, ON)
//  private fun appLovin(activity: Activity): Unit = AppLovinPrivacySettings.setHasUserConsent(true, activity)
//  private fun vungle(): Unit = VunglePrivacySettings.setGDPRStatus(true, "v1.0.0")
//  private fun unity3d(activity: Activity): Unit = MetaData(activity).add("gdpr.consent", true).commit()
}