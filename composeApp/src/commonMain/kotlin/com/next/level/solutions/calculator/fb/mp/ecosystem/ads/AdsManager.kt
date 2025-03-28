package com.next.level.solutions.calculator.fb.mp.ecosystem.ads

import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.app_open.AdsAppOpen
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.inter.AdsInter
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.AdsNative

interface AdsManager {
  val state: Boolean
  val inter: AdsInter
  val native: AdsNative
  val appOpen: AdsAppOpen

  fun init(onComplete: () -> Unit)
  fun consentState(): Boolean
}