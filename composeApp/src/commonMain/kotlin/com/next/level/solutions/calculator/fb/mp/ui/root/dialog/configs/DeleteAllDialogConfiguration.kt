@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser_history.dialog.DeleteAllDialogComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data class DeleteAllDialogConfiguration(
  private val agreed: () -> Unit,
) : RootComponent.DialogConfiguration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return DeleteAllDialogComponent.Handler(
      agreed = agreed,
    )
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::DeleteAllDialogComponent, context)
  }
}

fun RootComponent.DialogConfiguration.Companion.deleteAllDialog(
  agreed: () -> Unit,
): DeleteAllDialogConfiguration {
  return DeleteAllDialogConfiguration(
    agreed = agreed,
  )
}