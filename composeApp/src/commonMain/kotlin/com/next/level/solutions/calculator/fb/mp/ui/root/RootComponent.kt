package com.next.level.solutions.calculator.fb.mp.ui.root

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.NativeSize
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

class RootComponent(
  componentContext: ComponentContext,
  navigation: StackNavigation<Configuration>,
  dialogNavigation: SlotNavigation<DialogConfiguration>,
  private val factory: KoinFactory,
) : ComponentContext by componentContext {

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

  fun content(): @Composable () -> Unit = { RootContent(component = this) }

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
    return with(this) { factory.get(componentContext) }
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  abstract class Child(
    private val adsManager: AdsManager,
  ) {
    interface Action

    abstract fun content(): @Composable () -> Unit
    abstract fun action(action: Action)

    fun nativeAdCard(
      size: NativeSize?,
      modifier: Modifier = Modifier,
      loadAtDispose: Boolean = true,
      color: Color? = null,
    ): @Composable ColumnScope.() -> Unit = {
      adsManager.native.ad(
        size = size,
        modifier = modifier,
        loadAtDispose = loadAtDispose,
        color = color,
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