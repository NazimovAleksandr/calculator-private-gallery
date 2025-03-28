package com.next.level.solutions.calculator.fb.mp.ui.screen.ad.inter

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.DialogConfiguration
import com.next.level.solutions.calculator.fb.mp.ui.root.thanksDialog

class InterDialogComponent(
  componentContext: ComponentContext,
  val adsManager: AdsManager,
  private val dialogNavigation: SlotNavigation<DialogConfiguration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  @Composable
  override fun content() {
    InterDialogContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    when (action) {
      is Action.Close -> {
        dialogNavigation.dismiss()
        dialogNavigation.activate(DialogConfiguration.thanksDialog())
      }
    }
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler : InstanceKeeper.Instance

  sealed interface Action : RootComponent.Child.Action {
    object Close : Action
  }
}