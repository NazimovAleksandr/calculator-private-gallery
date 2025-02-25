package com.next.level.solutions.calculator.fb.mp.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.data.datastore.AppDatastore
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.NativeSize
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import com.next.level.solutions.calculator.fb.mp.utils.Logger
import kotlinx.serialization.Serializable

class RootComponent(
  componentContext: ComponentContext,
  navigation: StackNavigation<Configuration>,
  private val dialogNavigation: SlotNavigation<DialogConfiguration>,
  private val appDatastore: AppDatastore,
  private val factory: KoinFactory,
) : ComponentContext by componentContext, InstanceKeeper.Instance {

  init {
    Logger.w("TAG_ROOT", "RootComponent init = $this")
  }

  val stack: Value<ChildStack<*, Child>> = childStack(
    source = navigation,
    serializer = Configuration.serializer(),
    initialConfiguration = SplashConfiguration,
    handleBackButton = true,
    childFactory = ::child,
  )

  val dialog: Value<ChildSlot<*, Child>> = childSlot(
    source = dialogNavigation,
    serializer = DialogConfiguration.serializer(),
    handleBackButton = true,
    childFactory = ::child,
  )

  private val _model: MutableValue<Model> by lazy { MutableValue(Model()) }
  val model: Value<Model> get() = _model

  fun content(): @Composable () -> Unit = { RootContent(component = this) }

  fun action(action: Child.Action) {
    action.updateModel()

    launchMain {
      action.doSomething()?.let {
        action(it)
      }
    }
  }

  private fun Child.Action.updateModel() {
    when (this) {
      is Action.LockOn -> update()
      is Action.LockOff -> update()
      is Action.ActivateCollapseSecurity -> update()
      is Action.DeactivateCollapseSecurity -> update()
    }
  }

  private fun Child.Action.doSomething(): Action? {
    toString()
    return null
  }

  private fun child(
    configuration: Configuration,
    componentContext: ComponentContext,
  ): Child = configuration.toChild(
    componentContext = componentContext,
  )

  private fun Configuration.toChild(
    componentContext: ComponentContext,
  ): Child {
    componentContext.instanceKeeper.put(componentContext, instanceKeeper())
    componentContext.instanceKeeper.put("RootComponent", this@RootComponent)
    return with(this) { factory.get(componentContext) }
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
    componentContext.instanceKeeper.put("RootComponent", this@RootComponent)
    return with(this) { factory.get(componentContext) }
  }

  private fun Action.ActivateCollapseSecurity.update() {
    toString()

    _model.update {
      it.copy(collapseSecurity = true)
    }
  }

  private fun Action.DeactivateCollapseSecurity.update() {
    toString()

    _model.update {
      it.copy(collapseSecurity = false)
    }
  }

  private fun Action.LockOn.update() {
    toString()

    if (_model.value.collapseSecurity) {
      _model.update {
        it.copy(appLocked = true)
      }

      launchMain {
        dialogNavigation.activate(
          DialogConfiguration.appLock(
            changeMode = false,
            password = appDatastore.passwordStateOnce(),
          )
        )
      }
    }
  }

  private fun Action.LockOff.update() {
    toString()

    _model.update {
      it.copy(appLocked = false)
    }
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  data class Model(
    val collapseSecurity: Boolean = false,
    val appLocked: Boolean = false,
  )

  sealed interface Action : Child.Action {
    object LockOn : Action
    object LockOff : Action
    object ActivateCollapseSecurity : Action
    object DeactivateCollapseSecurity : Action
  }

  abstract class Child(
    private val adsManager: AdsManager,
  ) {
    interface Action

    abstract fun content(): @Composable () -> Unit
    abstract fun action(action: Action)

    @Composable
    fun nativeAdCard(
      size: NativeSize?,
      modifier: Modifier = Modifier,
      loadAtDispose: Boolean = true,
      color: Color? = null,
    ) {
      adsManager.native.ad(
        size = size,
        modifier = modifier,
        loadAtDispose = loadAtDispose,
        color = color,
      ).invoke()
    }
  }

  @Serializable
  sealed interface Configuration {
    fun instanceKeeper(): InstanceKeeper.Instance
    fun KoinFactory.get(context: ComponentContext): Child
  }

  @Serializable
  sealed interface DialogConfiguration {
    fun instanceKeeper(): InstanceKeeper.Instance
    fun KoinFactory.get(context: ComponentContext): Child
  }
}