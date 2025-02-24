@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.dialog.reset.code.ResetCodeDialogComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data object ResetCodeDialogConfiguration : RootComponent.DialogConfiguration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return ResetCodeDialogComponent.Handler()
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::ResetCodeDialogComponent, context)
  }
}

fun RootComponent.DialogConfiguration.Companion.resetPasswordCode(): ResetCodeDialogConfiguration {
  return ResetCodeDialogConfiguration
}