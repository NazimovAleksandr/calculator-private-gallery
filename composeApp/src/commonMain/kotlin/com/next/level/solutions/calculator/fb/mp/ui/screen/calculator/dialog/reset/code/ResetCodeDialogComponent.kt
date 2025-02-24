package com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.dialog.reset.code

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.DialogConfiguration

class ResetCodeDialogComponent(
  componentContext: ComponentContext,
  adsManager: AdsManager,
  private val dialogNavigation: SlotNavigation<DialogConfiguration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  override fun content(): @Composable () -> Unit = {
    ResetCodeDialogContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    when (action) {
      is Action.Hide -> dialogNavigation.dismiss()
    }
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler : InstanceKeeper.Instance

  sealed interface Action : RootComponent.Child.Action {
    object Hide: Action
  }
}