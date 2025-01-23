package com.next.level.solutions.calculator.fb.mp.ui.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

class RootComponent(
  componentContext: ComponentContext,
  navigation: StackNavigation<Configuration>,
  private val factory: KoinFactory,
) : ComponentContext by componentContext {

  val stack: Value<ChildStack<*, Child>> = childStack(
    source = navigation,
    serializer = Configuration.serializer(),
    initialConfiguration = SplashConfiguration,
    handleBackButton = true,
    childFactory = ::child,
  )

  private fun child(
    configuration: Configuration,
    componentContext: ComponentContext,
  ): Child = configuration.toChild(
    componentContext = componentContext,
  )

  private fun Configuration.toChild(
    componentContext: ComponentContext,
  ): Child {
    componentContext.instanceKeeper.put(instanceKey, toInstanceKeeper())
    return with(this) { factory.get(componentContext) }
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  interface Child {
    fun toContent(): @Composable (Child) -> Unit
  }

  @Serializable
  sealed interface Configuration {
    val instanceKey: String
    fun toInstanceKeeper(): InstanceKeeper.Instance
    fun KoinFactory.get(context: ComponentContext): Child
  }
}