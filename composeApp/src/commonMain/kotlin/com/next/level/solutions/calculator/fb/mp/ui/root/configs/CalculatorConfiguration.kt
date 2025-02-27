@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.CalculatorComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data class CalculatorConfiguration(
  private val changeMode: Boolean,
  private val password: String,
) : RootComponent.Configuration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return CalculatorComponent.Handler(changeMode, password)
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::CalculatorComponent, context)
  }
}

fun RootComponent.Configuration.Companion.calculator(changeMode: Boolean, password: String): CalculatorConfiguration {
  return CalculatorConfiguration(changeMode, password)
}