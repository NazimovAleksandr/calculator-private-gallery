package com.next.level.solutions.calculator.fb.mp.ui.screen.lottie

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.extensions.core.getInstance
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent

class LottieComponent(
  componentContext: ComponentContext,
  private val adsManager: AdsManager,
  private val navigation: StackNavigation<RootComponent.Configuration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val root: RootComponent? = instanceKeeper.getInstance(key = "RootComponent") as? RootComponent

  private val _model: MutableValue<Model> by lazy { MutableValue(Model(rootComponent = root)) }
  val model: Value<Model> get() = _model

  override fun content(): @Composable () -> Unit = {
    LottieContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    launchMain {
      action.doSomething()?.let {
        action(it)
      }
    }
  }

  private fun RootComponent.Child.Action.doSomething(): Action? {
    when (this) {
      is Action.EndAnimation -> adsManager.inter.show { navigation.pop() }
    }

    return null
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler : InstanceKeeper.Instance

  data class Model(
    val rootComponent: RootComponent?,
  )

  sealed interface Action : RootComponent.Child.Action {
    object EndAnimation : Action
  }
}