@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.dialog.secure.question.SecureQuestionComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data class SecureQuestionConfiguration(
  private val secureQuestion: String,
  private val answer: (String) -> Unit,
) : RootComponent.DialogConfiguration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return SecureQuestionComponent.Handler(
      secureQuestion = secureQuestion,
      answer = answer,
    )
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::SecureQuestionComponent, context)
  }
}

fun RootComponent.DialogConfiguration.Companion.secureQuestion(
  secureQuestion: String,
  answer: (String) -> Unit,
): SecureQuestionConfiguration {
  return SecureQuestionConfiguration(
    secureQuestion = secureQuestion,
    answer = answer,
  )
}