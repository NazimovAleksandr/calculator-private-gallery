@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.screen.ad.inter.InterDialogComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

private var closeHandler: () -> Unit = {}

@Serializable
object InterDialogConfiguration : RootComponent.DialogConfiguration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return InterDialogComponent.Handler(
      close = closeHandler,
    )
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::InterDialogComponent, context)
  }
}

fun RootComponent.DialogConfiguration.Companion.interDialog(
  close: () -> Unit,
): InterDialogConfiguration {
  closeHandler = close
  return InterDialogConfiguration
}