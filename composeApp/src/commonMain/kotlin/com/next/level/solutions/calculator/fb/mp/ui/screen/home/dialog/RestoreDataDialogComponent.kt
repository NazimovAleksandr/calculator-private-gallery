package com.next.level.solutions.calculator.fb.mp.ui.screen.home.dialog

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent

class RestoreDataDialogComponent(
  componentContext: ComponentContext,
  adsManager: AdsManager,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  @Composable
  override fun content() {
    RestoreDataDialogContent()
  }

  override fun action(action: Action) {}

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler : InstanceKeeper.Instance
}