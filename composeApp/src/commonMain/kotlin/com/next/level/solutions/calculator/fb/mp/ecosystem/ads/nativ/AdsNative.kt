package com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

interface AdsNative {
  fun init()
  fun isInit(): Boolean
  fun load()
  fun destroy()

  fun ad(
    size: NativeSize?,
    modifier: Modifier,
    loadAtDispose: Boolean,
    color: Color?,
    dividerSize: DividerSize,
  ): @Composable ColumnScope.() -> Unit
}

class DividerSize(
  val top: Dp = 1.dp,
  val bottom: Dp = 1.dp,
)