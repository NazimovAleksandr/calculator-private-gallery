package com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

interface AdsNative {
  fun init()
  fun load()
  fun destroy()

  fun ad(
    size: NativeSize?,
    modifier: Modifier = Modifier,
    loadAtDispose: Boolean = true,
    color: Color? = null,
  ): @Composable () -> Unit
}