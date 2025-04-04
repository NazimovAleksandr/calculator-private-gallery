@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.dialog.secure.question.SecureQuestionDialogComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

private var answerHandler: (answer: String) -> Unit = {}

@Serializable
data class SecureQuestionDialogConfiguration(
  private val secureQuestion: String,
) : RootComponent.DialogConfiguration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return SecureQuestionDialogComponent.Handler(
      secureQuestion = secureQuestion,
      answer = answerHandler,
    )
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::SecureQuestionDialogComponent, context)
  }
}

fun RootComponent.DialogConfiguration.Companion.secureQuestionDialog(
  secureQuestion: String,
  answer: (String) -> Unit,
): SecureQuestionDialogConfiguration {
  answerHandler = answer

  return SecureQuestionDialogConfiguration(
    secureQuestion = secureQuestion,
  )
}