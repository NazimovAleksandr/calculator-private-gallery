@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.screen.security.question.SecureQuestionComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data class SecureQuestionConfiguration(
  private val changeMode: Boolean,
  private val initQuestion: String?,
  private val initAnswer: String?,
) : RootComponent.Configuration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return SecureQuestionComponent.Handler(
      changeMode = changeMode,
      initQuestion = initQuestion,
      initAnswer = initAnswer,
    )
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::SecureQuestionComponent, context)
  }
}

fun RootComponent.Configuration.Companion.secureQuestion(
  changeMode: Boolean = false,
  initQuestion: String? = null,
  initAnswer: String? = null,
): SecureQuestionConfiguration {
  return SecureQuestionConfiguration(
    changeMode = changeMode,
    initQuestion = initQuestion,
    initAnswer = initAnswer,
  )
}