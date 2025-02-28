package com.next.level.solutions.calculator.fb.mp.ui.screen.onboarding

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.data.datastore.AppDatastore
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.language

class OnboardingComponent(
  componentContext: ComponentContext,
  private val adsManager: AdsManager,
  private val appDatastore: AppDatastore,
  private val navigation: StackNavigation<RootComponent.Configuration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val _model: MutableValue<Model> by lazy { MutableValue(initialModel()) }
  val model: Value<Model> get() = _model

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

  private fun initialModel(): Model {
    //    analytics.browser.browserOpen()

    return Model(
      withAd = adsManager.native.isInit(), /*todo remoteConfig*/
    )
  }

  private suspend fun RootComponent.Child.Action.doSomething(): Action? {
    when (this) {
      is Action.Skip -> {
        appDatastore.policyState(true)
        navigation.replaceCurrent(RootComponent.Configuration.language(changeMode = false))
      }
    }

    return null
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler : InstanceKeeper.Instance

  data class Model(
    val withAd: Boolean,
  )

  sealed interface Action : RootComponent.Child.Action {
    object Skip : Action
  }
}