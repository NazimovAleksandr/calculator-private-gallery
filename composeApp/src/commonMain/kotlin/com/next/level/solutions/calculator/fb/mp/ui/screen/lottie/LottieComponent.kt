package com.next.level.solutions.calculator.fb.mp.ui.screen.lottie

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.data.datastore.AppDatastore
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.extensions.core.getRootComponent
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent

class LottieComponent(
  componentContext: ComponentContext,
  adsManager: AdsManager,
  private val appDatastore: AppDatastore,
  private val navigation: StackNavigation<RootComponent.Configuration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val _model: MutableValue<Model> by lazy { MutableValue(initModel()) }
  val model: Value<Model> get() = _model

  init {
    val rootComponent: RootComponent = getRootComponent()

    rootComponent.model.subscribe { rootModel ->
      _model.update { it.copy(appLocked = rootModel.appLocked) }
    }
  }

  @Composable
  override fun content() {
    LottieContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    launchMain {
      action.doSomething()?.let {
        action(it)
      }
    }
  }

  private fun initModel(): Model {
    return Model(
      darkTheme = appDatastore.localStore.darkTheme
    )
  }

  private fun RootComponent.Child.Action.doSomething(): Action? {
    when (this) {
      is Action.EndAnimation -> navigation.pop()//adsManager.inter.show {  }
    }

    return null
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler : InstanceKeeper.Instance

  data class Model(
    val darkTheme: Boolean,
    val appLocked: Boolean = false,
  )

  sealed interface Action : RootComponent.Child.Action {
    object EndAnimation : Action
  }
}