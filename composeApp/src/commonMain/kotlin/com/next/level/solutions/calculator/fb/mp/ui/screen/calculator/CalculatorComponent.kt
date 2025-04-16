package com.next.level.solutions.calculator.fb.mp.ui.screen.calculator

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
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
import com.next.level.solutions.calculator.fb.mp.extensions.core.getRootComponent
import com.next.level.solutions.calculator.fb.mp.extensions.core.instance
import com.next.level.solutions.calculator.fb.mp.extensions.core.launch
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.Child
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.Configuration
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.DialogConfiguration
import com.next.level.solutions.calculator.fb.mp.ui.root.SecureQuestionDialogConfiguration
import com.next.level.solutions.calculator.fb.mp.ui.root.calculator
import com.next.level.solutions.calculator.fb.mp.ui.root.home
import com.next.level.solutions.calculator.fb.mp.ui.root.resetPasswordCode
import com.next.level.solutions.calculator.fb.mp.ui.root.secureQuestion
import com.next.level.solutions.calculator.fb.mp.ui.root.secureQuestionDialog
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.buttons.Buttons
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.buttons.operation
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.dialog.reset.code.ResetCodeDialogComponent
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.dialog.secure.question.SecureQuestionDialogComponent
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.math.parser.MathParser
import com.next.level.solutions.calculator.fb.mp.utils.Logger
import kotlinx.collections.immutable.ImmutableList

class CalculatorComponent(
  componentContext: ComponentContext,
  private val adsManager: AdsManager,
  private val appDatastore: AppDatastore,
  private val mathParser: MathParser,
  private val navigation: StackNavigation<Configuration>,
  private val dialogNavigation: SlotNavigation<DialogConfiguration>,
) : Child(adsManager), ComponentContext by componentContext {

  private val rootComponent: RootComponent = getRootComponent()
  private val handler: Handler = instance<Handler>(componentContext)

  private val _model: MutableValue<Model> by lazy { MutableValue(initialModel()) }
  val model: Value<Model> get() = _model

  private val innerDialogNavigation: SlotNavigation<DialogConfiguration> = SlotNavigation()

  private val enteredNumberRaw: MutableList<String> = mutableListOf()
  private val allNumberRaw: MutableList<String> = mutableListOf()

  private var checkedPasswordCount: Int = 0

  private var secureAnswer: String = ""

  private val buttons = Buttons()

  val dialog: Value<ChildSlot<*, Child>> = childSlot(
    source = innerDialogNavigation,
    serializer = DialogConfiguration.serializer(),
    handleBackButton = true,
    childFactory = ::child,
  )

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

  @Composable
  override fun content() {
    CalculatorContent(component = this)
  }

  override fun action(action: Child.Action) {
    action.updateModel()

    launchMain {
      action.doSomething()?.let {
        action(it)
      }
    }
  }

  private fun initialModel(): Model {
//    analytics.calculator.pinCreateScreenOpen()

    val creatingPassword = handler.password.isEmpty() || handler.changeMode

    return Model(
      rootModel = rootComponent.model,
      buttons = buttons.getButtons(),
      creatingPassword = creatingPassword,
      backHandlerEnabled = !handler.changeMode || handler.lockMode,
      enteredNumber = if (creatingPassword) "****" else ""
    )
  }

  private fun child(
    configuration: DialogConfiguration,
    componentContext: ComponentContext,
  ): Child = configuration.toChild(
    componentContext = componentContext,
  )

  private fun DialogConfiguration.toChild(
    componentContext: ComponentContext,
  ): Child {
    componentContext.instanceKeeper.put(componentContext, instanceKeeper())

    return when (this) {
      is SecureQuestionDialogConfiguration -> SecureQuestionDialogComponent(
        componentContext = componentContext,
        adsManager = adsManager,
        dialogNavigation = innerDialogNavigation,
      )

      else -> ResetCodeDialogComponent(
        componentContext = componentContext,
        adsManager = adsManager,
        dialogNavigation = innerDialogNavigation,
      )
    }
  }

  private fun Child.Action.updateModel() {
    when (this) {
      is Action.CalculatorButtonClick -> update()
      is Action.SecureQuestion -> update()
      is Action.ResetPassword -> update()
      is Action.SetPassword -> update()
    }
  }

  private fun Child.Action.doSomething(): Action? {
    when (this) {
      is Action.SecureAnswer -> doSomething()
      is Action.ChangeTheme -> doSomething()
    }

    return null
  }

  private fun Action.CalculatorButtonClick.update() {
    if (type == buttons.doubleZero) {
      action(Action.CalculatorButtonClick(buttons.zero))
      action(Action.CalculatorButtonClick(buttons.zero))

      return
    }

    val creatingPassword = _model.value.creatingPassword
    val enteredNumberRawSize = enteredNumberRaw.size

    if (
      creatingPassword
      && !type.forPassword()
    ) return

    if (
      creatingPassword
      && enteredNumberRawSize < 4
      && type == buttons.equal
    ) return

    if (
      creatingPassword
      && enteredNumberRawSize >= 4
      && type != buttons.delete
      && type != buttons.clear
      && type != buttons.equal
    ) return

    val enteredRaw: List<String> = enteredNumberRaw.toList()
    val allRaw: List<String> = allNumberRaw.toList()

    val raw: Pair<List<String>, List<String>> = when (type) {
      buttons.plus,
      buttons.minus,
      buttons.divide,
      buttons.multiply,
        -> {
        when {
          enteredRaw.isEmpty() -> enteredRaw + type to enteredRaw
          allRaw.isEmpty() -> emptyList<String>() to enteredRaw + type
          allRaw.last().operation() && enteredRaw.isEmpty() -> enteredRaw to allRaw.dropLast(1) + type
          else -> emptyList<String>() to allRaw + enteredRaw + type
        }
      }

      buttons.zero -> when {
        enteredRaw.contains(buttons.point) -> enteredRaw + type to allRaw
        enteredRaw.firstOrNull().isNotNullAndZero() && creatingPassword -> enteredRaw + type to allRaw
        enteredRaw.firstOrNull().isNotZero() && !creatingPassword -> enteredRaw + type to allRaw
        else -> enteredRaw/* + type*/ to allRaw
      }

      buttons.point -> when {
        enteredRaw.contains(buttons.point) -> enteredRaw to allRaw
        else -> enteredRaw + type to allRaw
      }

      buttons.equal -> when {
        creatingPassword -> allRaw to allRaw.also { checkPassword() }
        allRaw.isEmpty() && enteredRaw.isPassword() -> emptyList<String>() to allRaw.also { toHome() }
        allRaw.isEmpty() && enteredRaw.isResetPassword() -> enteredRaw to allRaw.also { resetPassword() }
        else -> allRaw + enteredRaw to emptyList()
      }

      buttons.delete -> when {
        enteredRaw.size > 1 -> enteredRaw.dropLast(1) to allRaw
        else -> emptyList<String>() to allRaw
      }

      buttons.percent -> when {
        allRaw.isEmpty() && enteredRaw.isEmpty() ||
            allRaw.isEmpty() && enteredRaw.size == 1 && enteredRaw[0].isZero()
          -> enteredRaw to emptyList()

        allRaw.isEmpty() -> null.percent(enteredRaw.asString()).toList().map { it.toString() } to emptyList()

        else ->  {
          val entered = allRaw
            .dropLast(1)
            .asString()
            .calculate()
            .percent(enteredRaw.asString())
            .toList()
            .map { it.toString() }

          entered to allRaw
        }
      }

      buttons.clear -> emptyList<String>() to emptyList()

      else -> when {
        enteredRaw.firstOrNull()
          .isZero() && !enteredRaw.contains(buttons.point) -> emptyList<String>() + type to allRaw

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
      buttons.equal -> {
        val result = enteredNumberRaw.asString().calculate()

        enteredNumberRaw.clear()
        enteredNumberRaw.addAll(result.toList().map { it.toString() })

        result
      }

      else -> enteredNumberRaw.asString()
    }

    _model.update {
      it.copy(
        enteredNumber = prepare(enteredNumber),
        allNumber = allNumberRaw.asString()
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

  private fun Action.ChangeTheme.doSomething() {
    rootComponent.action(RootComponent.Action.ChangeTheme(darkTheme = darkTheme))
  }

  private fun prepare(newValue: String): String {
    return when {
      _model.value.creatingPassword -> {
        when (newValue.length) {
          4 -> newValue
          3 -> "$newValue*"
          2 -> "$newValue**"
          1 -> "$newValue***"
          else -> "****"
        }
      }

      else -> newValue
    }
  }

  private fun List<String>.asString(): String {
    return joinToString("")
  }

  private fun String?.percent(percent: String): String {
    return when (this) {
      null -> "$percent%".calculate().also {
        Logger.d(TAG, "percent 1: $it")
      }
      else -> "$percent%*$this".calculate().also {
        Logger.d(TAG, "percent 2: $it")
      }
    }
  }

  private fun String.calculate(): String {
    if (isEmpty()) return this

    return mathParser
      .calculate(this)
      .let {
        when {
          it.toString().endsWith(".0") -> it.toInt()
          else -> it
        }
      }
      .toString()
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

  private fun List<String>.isPassword(): Boolean {
    return (asString() == handler.password)
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
          val nav = when {
            handler.lockMode -> innerDialogNavigation
            else -> dialogNavigation
          }

          nav.activate(
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
    launchMain {
      val nav = when {
        handler.lockMode -> innerDialogNavigation
        else -> dialogNavigation
      }

      nav.activate(DialogConfiguration.resetPasswordCode())
    }
  }

  private fun List<String>.isResetPassword(): Boolean {
    return (asString() == RESET_PASSWORD_CODE)
//      .also { if (it) analytics.calculator.resetCodeEntered() }
  }

  private fun resetPassword() {
    checkedPasswordCount = 0
    launch { appDatastore.passwordState("") }
    navigation.replaceCurrent(
      Configuration.calculator(
        changeMode = handler.changeMode,
        password = ""
      )
    )
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
//    adsManager.inter.show {
//    }
    when (handler.lockMode) {
      true -> {
        rootComponent.action(RootComponent.Action.LockOff)
        dialogNavigation.dismiss()
      }

      else -> navigation.replaceCurrent(Configuration.home())
    }
  }

  private fun String?.isZero(): Boolean {
    return this == buttons.zero
  }

  private fun String?.isNotZero(): Boolean {
    return this != buttons.zero
  }

  private fun String?.isNotNullAndZero(): Boolean {
    return this!= null && this != buttons.zero
  }

  private fun String?.forPassword(): Boolean {
    return this != buttons.point
        && this != buttons.plus
        && this != buttons.minus
        && this != buttons.divide
        && this != buttons.multiply
        && this != buttons.percent
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler(
    val changeMode: Boolean,
    val password: String,
    val lockMode: Boolean = false,
  ) : InstanceKeeper.Instance

  data class Model(
    val rootModel: Value<RootComponent.Model>,
    val creatingPassword: Boolean,
    val backHandlerEnabled: Boolean = true,
    val enteredNumber: String = "",
    val password: String = "",
    val allNumber: String = "",
    val secureQuestion: String = "",
    val buttons: ImmutableList<ImmutableList<String>>,
  )

  sealed interface Action : Child.Action {
    class ChangeTheme(val darkTheme: Boolean) : Action

    class CalculatorButtonClick(val type: String) : Action
    class SetPassword(val password: String) : Action

    class SecureQuestion(val value: String) : Action
    class SecureAnswer(val value: String) : Action

    object ResetPassword : Action
  }
}