@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.screen.browser.BrowserComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data class BrowserConfiguration(
  private val initUrl: String,
) : RootComponent.Configuration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return BrowserComponent.Handler(initUrl)
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::BrowserComponent, context)
  }
}

fun RootComponent.Configuration.Companion.browser(initUrl: String = "https://www.google.com/"): BrowserConfiguration {
  return BrowserConfiguration(initUrl)
}