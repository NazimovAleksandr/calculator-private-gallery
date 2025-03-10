package com.next.level.solutions.calculator.fb.mp.ecosystem.ads

import android.app.Activity
import android.telephony.TelephonyManager
import androidx.core.content.getSystemService
import com.google.android.gms.ads.MobileAds
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.app_open.AdsAppOpen
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.inter.AdsInter
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.AdsNative

class AdsManagerImpl(
  private val activity: Activity,
  override val inter: AdsInter,
  override val native: AdsNative,
  override val appOpen: AdsAppOpen,
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

//    mintegral(activity)
//    appLovin(activity)

//    MobileAds.initialize(activity.application) {
////      unity3d(activity)
////      vungle()
//
//      inter.load()
//      native.init()
//      appOpen.load()
      onComplete.invoke()
//    }

    init = true
  }

//  private fun mintegral(activity: Activity): Unit? = MBridgeSDKFactory.getMBridgeSDK()?.setConsentStatus(activity, ON)
//  private fun appLovin(activity: Activity): Unit = AppLovinPrivacySettings.setHasUserConsent(true, activity)
//  private fun vungle(): Unit = VunglePrivacySettings.setGDPRStatus(true, "v1.0.0")
//  private fun unity3d(activity: Activity): Unit = MetaData(activity).add("gdpr.consent", true).commit()
}