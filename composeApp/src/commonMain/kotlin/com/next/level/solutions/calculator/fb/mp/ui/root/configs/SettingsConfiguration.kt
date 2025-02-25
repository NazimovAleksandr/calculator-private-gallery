@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.screen.settings.SettingsComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data object SettingsConfiguration : RootComponent.Configuration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return SettingsComponent.Handler()
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::SettingsComponent, context)
  }
}

fun RootComponent.Configuration.Companion.settings(): SettingsConfiguration {
  return SettingsConfiguration
}