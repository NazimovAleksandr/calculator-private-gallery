@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.screen.onboarding.OnboardingComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data object OnboardingConfiguration : RootComponent.Configuration {
  override val instanceKey: String = OnboardingComponent.INSTANCE_KEY

  override fun instanceKeeper(): InstanceKeeper.Instance {
    return OnboardingComponent.Handler()
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::OnboardingComponent, context)
  }
}

fun RootComponent.Configuration.Companion.onboarding(): OnboardingConfiguration {
  return OnboardingConfiguration
}