package com.next.level.solutions.calculator.fb.mp.ui.root

import androidx.compose.foundation.layout.ColumnScope
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
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.DividerSize
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.NativeSize
import com.next.level.solutions.calculator.fb.mp.ecosystem.analytics.AppAnalytics
import com.next.level.solutions.calculator.fb.mp.ecosystem.billing.BillingManager
import com.next.level.solutions.calculator.fb.mp.ecosystem.config.AppConfig
import com.next.level.solutions.calculator.fb.mp.expect.AppEvent
import com.next.level.solutions.calculator.fb.mp.expect.AppEventListener
import com.next.level.solutions.calculator.fb.mp.expect.AppUpdate
import com.next.level.solutions.calculator.fb.mp.extensions.core.hexString
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import com.next.level.solutions.calculator.fb.mp.utils.Logger
import kotlinx.serialization.Serializable
import kotlin.random.Random

class RootComponent(
  componentContext: ComponentContext,
  navigation: StackNavigation<Configuration>,
  private val dialogNavigation: SlotNavigation<DialogConfiguration>,
  private val appDatastore: AppDatastore,
  private val adsManager: AdsManager,
  private val factory: KoinFactory,
  private val appConfig: AppConfig,
  private val appUpdate: AppUpdate,
  private val appAnalytics: AppAnalytics,
  private val billingManager: BillingManager,
  appEventListener: AppEventListener,
) : ComponentContext by componentContext, InstanceKeeper.Instance {

  init {
    Logger.w("RootComponent", "init: hex = ${this.hexString()}")

    adsManager.init {
      Logger.w("RootComponent", "adsManager.init")
    }

    appEventListener.set {
      when (it) {
        is AppEvent.AppOpen -> action(Action.AppOpen)
        is AppEvent.AppLock -> action(Action.LockOn)
      }
    }
  }

  val stack: Value<ChildStack<*, Child>> = childStack(
    key = Random.nextInt().toString(),
    source = navigation,
    serializer = Configuration.serializer(),
    initialConfiguration = SplashConfiguration,
    handleBackButton = true,
    childFactory = ::child,
  )

  val dialog: Value<ChildSlot<*, Child>> = childSlot(
    key = Random.nextInt().toString(),
    source = dialogNavigation,
    serializer = DialogConfiguration.serializer(),
    handleBackButton = true,
    childFactory = ::child,
  )

  private val _model: MutableValue<Model> by lazy { MutableValue(initModel()) }
  val model: Value<Model> get() = _model

  @Composable
  fun content() {
    RootContent(component = this)
    RootDialog(component = this)
  }

  fun action(action: Child.Action) {
    action.updateModel()

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

  private fun Child.Action.updateModel() {
    when (this) {
      is Action.LockOn -> update()
      is Action.LockOff -> update()
      is Action.ActivateCollapseSecurity -> update()
      is Action.DeactivateCollapseSecurity -> update()
      is Action.ChangeTheme -> update()
    }
  }

  private fun Child.Action.doSomething(): Action? {
    when (this) {
      is Action.AppOpen -> adsManager.appOpen.show {}

      is Action.InitAppConfig -> appConfig.init {
        appUpdate.checkAppUpdate(
          result =  { appAnalytics.logEvent("app_update", "result" to it) },
          type = appConfig.app.appUpdateType,
        )

        billingManager.init()
      }
    }

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

  private fun Action.ChangeTheme.update() {
    appDatastore.localStore.darkTheme = darkTheme

    _model.update {
      it.copy(darkTheme = darkTheme)
    }
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  data class Model(
    val darkTheme: Boolean,
    val collapseSecurity: Boolean = false,
    val appLocked: Boolean = false,
  )

  sealed interface Action : Child.Action {
    class ChangeTheme(val darkTheme: Boolean) : Action

    object LockOn : Action
    object LockOff : Action
    object AppOpen : Action
    object ActivateCollapseSecurity : Action
    object DeactivateCollapseSecurity : Action
    object InitAppConfig : Action
  }

  abstract class Child(
    private val adsManager: AdsManager,
  ) {
    interface Action

    @Composable
    abstract fun content()

    abstract fun action(action: Action)

    @Suppress("PropertyName", "MemberVisibilityCanBePrivate")
    val TAG: String = this::class.simpleName.toString()

    init {
      Logger.w(TAG, "init: hex = ${this.hexString()}")
    }

    @Composable
    fun ColumnScope.nativeAdCard(
      size: NativeSize?,
      modifier: Modifier = Modifier,
      loadAtDispose: Boolean = true,
      dividerSize: DividerSize = DividerSize(),
      color: Color? = null,
    ) {
      adsManager.native.ad(
        size = size,
        modifier = modifier,
        loadAtDispose = loadAtDispose,
        color = color,
        dividerSize = dividerSize,
      ).invoke(this)
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