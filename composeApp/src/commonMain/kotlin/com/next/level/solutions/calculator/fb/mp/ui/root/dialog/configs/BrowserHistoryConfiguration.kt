@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser_history.BrowserHistoryComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data class BrowserHistoryConfiguration(
  val open: (url: String) -> Unit,
) : RootComponent.DialogConfiguration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return BrowserHistoryComponent.Handler(
      open = open,
    )
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::BrowserHistoryComponent, context)
  }
}

fun RootComponent.DialogConfiguration.Companion.browserHistory(
  open: (url: String) -> Unit,
): BrowserHistoryConfiguration {
  return BrowserHistoryConfiguration(
    open = open,
  )
}