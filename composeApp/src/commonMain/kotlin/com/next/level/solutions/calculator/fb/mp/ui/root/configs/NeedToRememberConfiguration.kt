@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.screen.need.to.remember.NeedToRememberComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data class NeedToRememberConfiguration(
  private val changeMode: Boolean,
  private val question: String,
  private val answer: String,
) : RootComponent.Configuration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return NeedToRememberComponent.Handler(
      changeMode = changeMode,
      question = question,
      answer = answer,
    )
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::NeedToRememberComponent, context)
  }
}

fun RootComponent.Configuration.Companion.needToRemember(
  changeMode: Boolean,
  question: String,
  answer: String,
): NeedToRememberConfiguration {
  return NeedToRememberConfiguration(
    changeMode = changeMode,
    question = question,
    answer = answer,
  )
}