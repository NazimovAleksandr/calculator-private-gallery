package com.next.level.solutions.calculator.fb.mp.ecosystem.ads.inter

interface AdsInter {
  fun load()
  fun state(): Boolean
  fun show(closeCallback: () -> Unit)
}