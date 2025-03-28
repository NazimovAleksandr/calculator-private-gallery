package com.next.level.solutions.calculator.fb.mp.ui.screen.need.to.remember

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.constants.RESET_PASSWORD_CODE
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.extensions.core.instance
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.Configuration
import com.next.level.solutions.calculator.fb.mp.ui.root.home

class NeedToRememberComponent(
  componentContext: ComponentContext,
  private val adsManager: AdsManager,
  private val navigation: StackNavigation<Configuration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val handler: Handler = instance<Handler>(componentContext)

  private val _model: MutableValue<Model> by lazy { MutableValue(handler.toModel()) }
  val model: Value<Model> get() = _model

  @Composable
  override fun content() {
    NeedToRememberContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    launchMain {
      when (action) {
        is Action.OK -> action.doSomething()
      }
    }
  }

  private fun Action.OK.doSomething() {
    toString()

//    adsManager.inter.show {
//    }
    when (handler.changeMode) {
      true -> navigation.pop()
      else -> navigation.replaceCurrent(Configuration.home())
    }
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler(
    val changeMode: Boolean,
    private val question: String,
    private val answer: String,
  ) : InstanceKeeper.Instance {
    fun toModel(): Model = Model(
      question = question,
      answer = answer,
    )
  }

  data class Model(
    val question: String,
    val answer: String,
    val resetCode: String = RESET_PASSWORD_CODE,
  )

  sealed interface Action : RootComponent.Child.Action {
    object OK : Action
  }
}