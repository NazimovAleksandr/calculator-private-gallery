@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.screen.lottie.LottieComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data object LottieConfiguration : RootComponent.Configuration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return LottieComponent.Handler()
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::LottieComponent, context)
  }
}

fun RootComponent.Configuration.Companion.lottie(): LottieConfiguration {
  return LottieConfiguration
}