package com.next.level.solutions.calculator.fb.mp.ecosystem.ads.app_open

interface AdsAppOpen {
  fun load()
  fun state(): Boolean
  fun isShown(): Boolean
  fun show(closeCallback: () -> Unit)
}