package com.next.level.solutions.calculator.fb.mp.ui.screen.calculator

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.constants.RESET_PASSWORD_CODE
import com.next.level.solutions.calculator.fb.mp.data.datastore.AppDatastore
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.extensions.core.instance
import com.next.level.solutions.calculator.fb.mp.extensions.core.launch
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.root.HiddenFilesConfiguration.hiddenFiles
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.Configuration
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.DialogConfiguration
import com.next.level.solutions.calculator.fb.mp.ui.root.calculator
import com.next.level.solutions.calculator.fb.mp.ui.root.resetPasswordCode
import com.next.level.solutions.calculator.fb.mp.ui.root.secureQuestion
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.composable.ButtonType
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.composable.isNotZero
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.composable.isZero
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.math.parser.MathParser
import com.next.level.solutions.calculator.fb.mp.utils.Logger

class CalculatorComponent(
  componentContext: ComponentContext,
  private val adsManager: AdsManager,
  private val appDatastore: AppDatastore,
  private val mathParser: MathParser,
  private val navigation: StackNavigation<Configuration>,
  private val dialogNavigation: SlotNavigation<DialogConfiguration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val handler: Handler = instance<Handler>(componentContext)

  private val _model: MutableValue<Model> = MutableValue(initialModel())
  val model: Value<Model> = _model

  private val enteredNumberRaw: MutableList<ButtonType> = mutableListOf()
  private val allNumberRaw: MutableList<ButtonType> = mutableListOf()

  private var checkedPasswordCount: Int = 0

  private var secureAnswer: String = ""

  init {
    launch {
      appDatastore.secureQuestionStateOnce()?.let {
        action(Action.SecureQuestion(it))
      }

      appDatastore.secureAnswerStateOnce()?.let {
        secureAnswer = it
      }
    }
  }

  override fun content(): @Composable () -> Unit = {
    CalculatorContent(component = this)
  }

  override fun action(action: RootComponent.Child.Action) {
    action.updateModel()

    launchMain {
      action.doSomething()?.let {
        action(it)
      }
    }
  }

  private fun initialModel(): Model {
//    analytics.calculator.pinCreateScreenOpen()

    return Model(
      creatingPassword = handler.password.isEmpty() || handler.changeMode,
      backHandlerEnabled = !handler.changeMode,
    )
  }

  private fun RootComponent.Child.Action.updateModel() {
    when (this) {
      is Action.CalculatorButtonClick -> update()
      is Action.SecureQuestion -> update()
      is Action.ResetPassword -> update()
      is Action.SetPassword -> update()
    }
  }

  private fun RootComponent.Child.Action.doSomething(): Action? {
    when (this) {
      is Action.SecureAnswer -> doSomething()
    }

    return null
  }

  private fun Action.CalculatorButtonClick.update() {
    if (
      _model.value.creatingPassword
      && !type.forPassword
    ) {
      return
    }

    if (
      _model.value.creatingPassword
      && _model.value.enteredNumber.length < 4
      && type == ButtonType.Equal
    ) {
      return
    }

    if (
      _model.value.creatingPassword
      && _model.value.enteredNumber.length >= 4
      && type != ButtonType.Delete
      && type != ButtonType.Equal
    ) {
      return
    }

    val enteredRaw: List<ButtonType> = enteredNumberRaw.toList()
    val allRaw: List<ButtonType> = allNumberRaw.toList()

    val raw: Pair<List<ButtonType>, List<ButtonType>> = when (type) {
      ButtonType.Plus,
      ButtonType.Minus,
      ButtonType.Divide,
      ButtonType.Multiply,
        -> {
        when {
          allRaw.isEmpty() -> emptyList<ButtonType>() to allRaw + enteredRaw + type
          allRaw.last().operation && enteredRaw.isEmpty() -> enteredRaw to allRaw.dropLast(1) + type
          else -> emptyList<ButtonType>() to allRaw + enteredRaw + type
        }
      }

      ButtonType.Zero -> when {
        enteredRaw.contains(ButtonType.Point) -> enteredRaw + type to allRaw
        enteredRaw.firstOrNull().isNotZero() -> enteredRaw + type to allRaw
        else -> enteredRaw + type to allRaw
      }

      ButtonType.Point -> when {
        enteredRaw.contains(ButtonType.Point) -> enteredRaw to allRaw
        else -> enteredRaw + type to allRaw
      }

      ButtonType.Equal -> when {
        _model.value.creatingPassword -> allRaw to allRaw.also { checkPassword() }
        allRaw.isEmpty() && enteredRaw.isPassword() -> emptyList<ButtonType>() to allRaw.also { toHome() }
        allRaw.isEmpty() && enteredRaw.isResetPassword() -> enteredRaw to allRaw.also { resetPassword() }
        else -> allRaw + enteredRaw to emptyList()
      }

      ButtonType.Delete -> when {
        enteredRaw.size > 1 -> enteredRaw.dropLast(1) to allRaw
        else -> emptyList<ButtonType>() to allRaw
      }

      else -> when {
        enteredRaw.firstOrNull().isZero() && !enteredRaw.contains(ButtonType.Point) -> enteredRaw to allRaw
        else -> enteredRaw + type to allRaw
      }
    }

    enteredNumberRaw.clear()
    allNumberRaw.clear()

    val enteredData = when (raw.first.size > 15) {
      true -> raw.first.take(15)
      else -> raw.first
    }

    enteredNumberRaw.addAll(enteredData)
    allNumberRaw.addAll(raw.second)

    val enteredNumber = when (type) {
      ButtonType.Equal -> {
        val result = enteredNumberRaw.toNumber().calculate()

        enteredNumberRaw.clear()
        enteredNumberRaw.addAll(result.toButtonType())

        result
      }

      else -> enteredNumberRaw.toNumber()
    }

    _model.update {
      it.copy(
        enteredNumber = enteredNumber,
        allNumber = allNumberRaw.toNumber()
      )
    }
  }

  private fun Action.SecureQuestion.update() {
    _model.update { it.copy(secureQuestion = value) }
  }

  private fun Action.ResetPassword.update() {
    toString()

    enteredNumberRaw.clear()
    allNumberRaw.clear()

    _model.update {
      it.copy(
        password = "",
        enteredNumber = "",
      )
    }
  }

  private fun Action.SetPassword.update() {
//    analytics.calculator.pinFirstEntered()

    enteredNumberRaw.clear()
    allNumberRaw.clear()

    _model.update {
      it.copy(
        password = password,
        backHandlerEnabled = true,
        enteredNumber = "",
      )
    }
  }

  private fun Action.SecureAnswer.doSomething() {
    when (value.trim() == secureAnswer) {
      true -> {
//        analytics.calculator.secretAnswerCorrect()
        resetPassword()
      }

      else -> {
//        analytics.calculator.secretAnswerIncorrect()
        resetPasswordCode()
      }
    }
  }

  private fun <E : ButtonType> List<E>.toNumber(): String {
    return joinToString("") { it.value.toString() }
  }

  private fun String.calculate(): String {
    if (isEmpty()) return this

    return mathParser
      .calculate(this)
      .let {
        when {
          this.contains(".") -> it
          this.contains("/") -> it
          else -> it.toInt()
        }
      }
      .toString()
  }

  private fun String.toButtonType(): List<ButtonType> {
    return toList().map {
      when (it) {
        ButtonType.Zero.value -> ButtonType.Zero
        ButtonType.One.value -> ButtonType.One
        ButtonType.Two.value -> ButtonType.Two
        ButtonType.Three.value -> ButtonType.Three
        ButtonType.Four.value -> ButtonType.Four
        ButtonType.Five.value -> ButtonType.Five
        ButtonType.Six.value -> ButtonType.Six
        ButtonType.Seven.value -> ButtonType.Seven
        ButtonType.Eight.value -> ButtonType.Eight
        ButtonType.Nine.value -> ButtonType.Nine
        ButtonType.Point.value -> ButtonType.Point

        else -> ButtonType.Zero
      }
    }
  }

  private fun checkPassword() {
    val password = _model.value.password
    val enteredNumber = _model.value.enteredNumber

    when {
      password.isEmpty() -> action(Action.SetPassword(enteredNumber))
      password == enteredNumber -> savePassword(password)
      else -> confirmPasswordError()
    }
  }

  private fun <E : ButtonType> List<E>.isPassword(): Boolean {
    return (toNumber() == handler.password)
      .also {
        if (size == 4) {
          when (it) {
            true -> checkedPasswordCount = 0
            else -> checkedPasswordCount += 1
          }
        }
      }
      .also {
        if (size == 4 && checkedPasswordCount >= 4) {
          secureQuestion()
        }
      }
  }

  private fun secureQuestion() {
//    analytics.calculator.incorrectPin3Attempts()

    when (secureAnswer != "") {
      true -> {
//        analytics.calculator.secretQuestionPrompt()
        launchMain {
          dialogNavigation.activate(
            DialogConfiguration.secureQuestionDialog(_model.value.secureQuestion) {
              action(Action.SecureAnswer(it))
            }
          )
        }
      }

      else -> {
//        analytics.calculator.resetInfoPrompt()
        resetPasswordCode()
      }
    }
  }

  private fun resetPasswordCode() {
    launchMain { dialogNavigation.activate(DialogConfiguration.resetPasswordCode()) }
  }

  private fun <E : ButtonType> List<E>.isResetPassword(): Boolean {
    return (toNumber() == RESET_PASSWORD_CODE)
//      .also { if (it) analytics.calculator.resetCodeEntered() }
  }

  private fun resetPassword() {
    checkedPasswordCount = 0
    launch { appDatastore.passwordState("") }
    navigation.replaceCurrent(Configuration.calculator(changeMode = handler.changeMode, password = ""))
  }

  private fun savePassword(password: String) {
//    analytics.calculator.pinSecondEntered()

    launchMain {
      appDatastore.passwordState(password)

      when (false) {
        true -> {
//        analytics.calculator.passwordChange()
          navigation.pop()
        }

        else -> {
//        analytics.calculator.pinCreatedSuccess()
          navigation.replaceCurrent(Configuration.secureQuestion())
        }
      }
    }
  }

  private fun confirmPasswordError() {
//    analytics.calculator.pinSecondEntered()
//    analytics.calculator.pinCreationFailed()

//    triggerSignal(Signal.ConfirmPasswordError)

    enteredNumberRaw.clear()
    _model.update { it.copy(enteredNumber = "") }
  }

  private fun toHome() {
    adsManager.inter.show {
      navigation.replaceCurrent(Configuration.hiddenFiles()) // todo HOME
    }
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler(
    val changeMode: Boolean,
    val password: String,
  ) : InstanceKeeper.Instance

  data class Model(
    val creatingPassword: Boolean,
    val backHandlerEnabled: Boolean = true,
    val enteredNumber: String = "",
    val password: String = "",
    val allNumber: String = "",
    val secureQuestion: String = "",
  )

  sealed interface Action : RootComponent.Child.Action {
    class CalculatorButtonClick(val type: ButtonType) : Action
    class SetPassword(val password: String) : Action

    class SecureQuestion(val value: String) : Action
    class SecureAnswer(val value: String) : Action

    object ResetPassword : Action
  }
}