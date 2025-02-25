@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.screen.home.HomeComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data object HomeConfiguration : RootComponent.Configuration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return HomeComponent.Handler()
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::HomeComponent, context)
  }
}

fun RootComponent.Configuration.Companion.home(): HomeConfiguration {
  return HomeConfiguration
}