package com.next.level.solutions.calculator.fb.mp.ui.screen.settings

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
import com.next.level.solutions.calculator.fb.mp.entity.ui.extra.SettingsModelUI
import com.next.level.solutions.calculator.fb.mp.entity.ui.extra.SettingsType
import com.next.level.solutions.calculator.fb.mp.entity.ui.extra.getSettingsItems
import com.next.level.solutions.calculator.fb.mp.expect.PlatformExp
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchIO
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.calculator
import com.next.level.solutions.calculator.fb.mp.ui.root.language
import com.next.level.solutions.calculator.fb.mp.ui.root.policyTos
import com.next.level.solutions.calculator.fb.mp.ui.root.secureQuestion
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.coroutines.flow.Flow

@Stable
class SettingsComponent(
  componentContext: ComponentContext,
  adsManager: AdsManager,
  private val appDatastore: AppDatastore,
  private val navigation: StackNavigation<RootComponent.Configuration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val _model: MutableValue<Model> by lazy { MutableValue(initialModel()) }
  val model: Value<Model> get() = _model

  @Composable
  override fun content() {
    SettingsContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    launchMain {
      action.doSomething()?.let {
        action(it)
      }
    }
  }

  private fun initialModel(): Model {
//    analytics.settings.screenOpen()

    return Model(
      items = getSettingsItems(),
      language = appDatastore.languageName(),
      tipToResetPassword = appDatastore.tipToResetPassword(),
    )
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
      SettingsModelUI.Privacy -> navigation.pushNew(RootComponent.Configuration.policyTos(tos = false))
      SettingsModelUI.Tos -> navigation.pushNew(RootComponent.Configuration.policyTos(tos = true))
      SettingsModelUI.Rate5Stars -> PlatformExp.openMarket()
      SettingsModelUI.Share -> PlatformExp.shareApp()
    }
  }

  /**
   * Store contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler : InstanceKeeper.Instance

  data class Model(
    val items: ImmutableMap<SettingsType, ImmutableList<SettingsModelUI>>,
    val language: Flow<String?>,
    val tipToResetPassword: Flow<Boolean?>,
    val resetCode: String = RESET_PASSWORD_CODE,
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