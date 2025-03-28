package com.next.level.solutions.calculator.fb.mp.ecosystem.ads.inter

import androidx.compose.runtime.State

interface AdsInter {
  fun load()
  fun state(): State<Boolean>
  fun show(closeCallback: () -> Unit)
}