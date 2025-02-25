@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.screen.home.dialog.NeedAccessDialogComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data class NeedAccessDialogConfiguration(
  private val agreed: () -> Unit,
) : RootComponent.DialogConfiguration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return NeedAccessDialogComponent.Handler(
      agreed = agreed,
    )
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::NeedAccessDialogComponent, context)
  }
}

fun RootComponent.DialogConfiguration.Companion.needAccessDialog(
  agreed: () -> Unit,
): NeedAccessDialogConfiguration {
  return NeedAccessDialogConfiguration(
    agreed = agreed,
  )
}