package com.next.level.solutions.calculator.fb.mp.ui.screen.policy.tos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.constants.RESET_PASSWORD_CODE
import com.next.level.solutions.calculator.fb.mp.data.datastore.AppDatastore
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.entity.ui.SettingsModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.SettingsType
import com.next.level.solutions.calculator.fb.mp.entity.ui.getSettingsItems
import com.next.level.solutions.calculator.fb.mp.extensions.core.instance
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchIO
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.calculator
import com.next.level.solutions.calculator.fb.mp.ui.root.language
import com.next.level.solutions.calculator.fb.mp.ui.root.secureQuestion
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.coroutines.flow.Flow

@Stable
class PolicyTosComponent(
  componentContext: ComponentContext,
  adsManager: AdsManager,
  private val appDatastore: AppDatastore,
  private val navigation: StackNavigation<RootComponent.Configuration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val handler: Handler = instance<Handler>(componentContext)

  private val _model: MutableValue<Model> by lazy { MutableValue(Model(handler.tos)) }
  val model: Value<Model> get() = _model

  override fun content(): @Composable () -> Unit = {
    PolicyTosContent(component = this)
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
      is Action.Back -> navigation.pop()
      is Action.HideTipToResetPassword -> launchIO { appDatastore.tipToResetPassword(false) }
      is Action.Item -> doSomething()
    }

    return null
  }

  private suspend fun Action.Item.doSomething() {
    when (item) {
      SettingsModelUI.ChangePassword -> navigation.pushNew(
        RootComponent.Configuration.calculator(
          changeMode = true,
          password = appDatastore.passwordStateOnce(),
        )
      )

      SettingsModelUI.ChangeSecurityQuestion -> navigation.pushNew(
        RootComponent.Configuration.secureQuestion(
          changeMode = true,
          initQuestion = appDatastore.secureQuestionStateOnce(),
          initAnswer = appDatastore.secureAnswerStateOnce(),
        )
      )

      SettingsModelUI.Language -> navigation.pushNew(RootComponent.Configuration.language(changeMode = true))
//      SettingsModelUI.Rate5Stars -> triggerSignal(Signal.Rate5Stars) todo
//      SettingsModelUI.Share -> triggerSignal(Signal.Share) todo
//      SettingsModelUI.Privacy -> navigate(AppGraph.Policy) todo
//      SettingsModelUI.Tos -> navigate(AppGraph.Tos) todo
      else -> {}
    }
  }

  /**
   * Store contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler(
    val tos: Boolean,
  ) : InstanceKeeper.Instance

  data class Model(
    val tos: Boolean,
  )

  sealed interface Action : RootComponent.Child.Action {
    object Back : Action
    object HideTipToResetPassword : Action
    class Item(val item: SettingsModelUI) : Action
  }
//
//  sealed interface Signal : Store.Signal {
//    object Rate5Stars: Signal
//    object Share: Signal
//  }
}