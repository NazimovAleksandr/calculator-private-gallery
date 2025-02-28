package com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.dialog.secure.question

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.extensions.core.instance
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.DialogConfiguration

class SecureQuestionDialogComponent(
  componentContext: ComponentContext,
  adsManager: AdsManager,
  private val dialogNavigation: SlotNavigation<DialogConfiguration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val handler: Handler = instance<Handler>(componentContext)

  private val _model: MutableValue<Model> by lazy { MutableValue(handler.toModel()) }
  val model: Value<Model> get() = _model

  @Composable
  override fun content() {
    SecureQuestionDialogContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    when (action) {
      is Action.Hide -> dialogNavigation.dismiss()
      is Action.Done -> handler.answer(action.answer)
    }
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler(
    private val secureQuestion: String,
    val answer: (String) -> Unit,
  ) : InstanceKeeper.Instance {
    fun toModel() = Model(
      secureQuestion = secureQuestion,
    )
  }

  data class Model(
    val secureQuestion: String = "",
  )

  sealed interface Action : RootComponent.Child.Action {
    object Hide: Action
    class Done(val answer: String): Action
  }
}