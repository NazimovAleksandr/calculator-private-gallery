package com.next.level.solutions.calculator.fb.mp.ui.screen.home.dialog

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.extensions.core.instance
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.DialogConfiguration

class NeedAccessDialogComponent(
  componentContext: ComponentContext,
  adsManager: AdsManager,
  private val dialogNavigation: SlotNavigation<DialogConfiguration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val handler: Handler = instance<Handler>(componentContext)

  @Composable
  override fun content() {
    NeedAccessDialogContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    when (action) {
      is Action.Hide -> dialogNavigation.dismiss()
      is Action.Agreed -> handler.agreed()
    }
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler(
    val agreed: () -> Unit,
  ) : InstanceKeeper.Instance

  sealed interface Action : RootComponent.Child.Action {
    object Hide: Action
    object Agreed: Action
  }
}