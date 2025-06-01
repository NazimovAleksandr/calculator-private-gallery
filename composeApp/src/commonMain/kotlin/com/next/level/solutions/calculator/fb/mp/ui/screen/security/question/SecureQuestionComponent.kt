package com.next.level.solutions.calculator.fb.mp.ui.screen.security.question

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.data.datastore.AppDatastore
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.extensions.core.instance
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.Configuration
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.DialogConfiguration
import com.next.level.solutions.calculator.fb.mp.ui.root.home
import com.next.level.solutions.calculator.fb.mp.ui.root.needToRemember

class SecureQuestionComponent(
  componentContext: ComponentContext,
  adsManager: AdsManager,
  private val appDatastore: AppDatastore,
  private val navigation: StackNavigation<Configuration>,
  private val dialogNavigation: SlotNavigation<DialogConfiguration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val handler: Handler = instance<Handler>(componentContext)

  private val _model: MutableValue<Model> by lazy { MutableValue(handler.toModel()) }
  val model: Value<Model> get() = _model

  private var question = ""
  private var answer = ""

  @Composable
  override fun content() {
    SecureQuestionContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    action.updateModel()

    launchMain {
      action.doSomething()?.let {
        action(it)
      }
    }
  }

  override fun interOff() {
    skip()
  }

  private suspend fun RootComponent.Child.Action.doSomething(): Action? {
    when (this) {
      is Action.Question -> question = value
      is Action.Answer -> answer = value
      is Action.SaveAnswer -> saveAnswer()
      is Action.Skip -> interOn(dialogNavigation)
    }

    return null
  }

  private fun RootComponent.Child.Action.updateModel() {
    when (this) {
      is Action.Answer -> update()
    }
  }

  private fun Action.Answer.update() {
    _model.update { it.copy(answerState = value.length > 1) }
  }

  private suspend fun saveAnswer() {
//    analytics.securityQuestion.secretQuestionSaved()
    appDatastore.secureQuestionState(question)
    appDatastore.secureAnswerState(answer.trim())

    navigation.replaceCurrent(Configuration.needToRemember(
      changeMode = handler.changeMode,
      question = question,
      answer = answer.trim(),
    ))
  }

  private fun skip() {
//    analytics.securityQuestion.secretQuestionSkipped()
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
    private val initQuestion: String?,
    private val initAnswer: String?,
  ) : InstanceKeeper.Instance {
    fun toModel(): Model = Model(initQuestion, initAnswer)
  }

  data class Model(
    val initQuestion: String? = null,
    val initAnswer: String? = null,
    val answerState: Boolean = initQuestion != null && initAnswer != null,
  )

  sealed interface Action : RootComponent.Child.Action {
    class Answer(val value: String) : Action
    class Question(val value: String) : Action
    object SaveAnswer : Action
    object Skip : Action
  }
}