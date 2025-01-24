package com.next.level.solutions.calculator.fb.mp.ecosystem.ads

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.app_open.AdsAppOpen
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.inter.AdsInter
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.AdsNative
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.NativeSize

actual fun adsManager(): AdsManager = object : AdsManager {
  override val inter: AdsInter
    get() = object : AdsInter {
      override fun load() {
        // TODO("Not yet implemented")
      }

      override fun state(): Boolean {
        // TODO("Not yet implemented")
        return false
      }

      override fun show(closeCallback: () -> Unit) {
        // TODO("Not yet implemented")
      }
    }

  override val native: AdsNative
    get() = object : AdsNative {
      override fun init() {
        // TODO("Not yet implemented")
      }

      override fun load() {
        // TODO("Not yet implemented")
      }

      override fun destroy() {
        // TODO("Not yet implemented")
      }

      override fun ad(
        size: NativeSize?,
        modifier: Modifier,
        loadAtDispose: Boolean,
        color: Color?,
      ): @Composable ColumnScope.() -> Unit {
        // TODO("Not yet implemented")
        return {}
      }
    }

  override val appOpen: AdsAppOpen
    get() = object : AdsAppOpen {
      override fun load() {
        // TODO("Not yet implemented")
      }

      override fun state(): Boolean {
        // TODO("Not yet implemented")
        return false
      }

      override fun isShown(): Boolean {
        // TODO("Not yet implemented")
        return false
      }

      override fun show(closeCallback: () -> Unit) {
        // TODO("Not yet implemented")
      }

    }

  override fun init(onComplete: () -> Unit) {
    // TODO("Not yet implemented")
  }
}