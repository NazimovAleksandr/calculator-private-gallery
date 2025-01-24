package com.next.level.solutions.calculator.fb.mp.ui.screen.onboarding

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.data.datastore.AppDatastore
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.root.LanguageConfiguration.language
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent

class OnboardingComponent(
  componentContext: ComponentContext,
  adsManager: AdsManager,
  private val appDatastore: AppDatastore,
  private val navigation: StackNavigation<RootComponent.Configuration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  override fun content(): @Composable () -> Unit = {
    OnboardingContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    launchMain {
      action.doSomething()?.let {
        action(it)
      }
    }
  }

  private suspend fun RootComponent.Child.Action.doSomething(): Action? {
    when (this) {
      is Action.Skip -> {
        appDatastore.policyState(true)
        navigation.replaceCurrent(RootComponent.Configuration.language())
      }
    }

    return null
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  companion object {
    val INSTANCE_KEY: String = this::class.toString()
  }

  class Handler : InstanceKeeper.Instance

  sealed interface Action : RootComponent.Child.Action {
    object Skip : Action
  }
}