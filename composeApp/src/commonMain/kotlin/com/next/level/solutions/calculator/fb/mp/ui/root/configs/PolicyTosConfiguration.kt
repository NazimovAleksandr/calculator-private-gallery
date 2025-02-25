@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.ui.screen.policy.tos.PolicyTosComponent
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import kotlinx.serialization.Serializable

@Serializable
data class PolicyTosConfiguration(
  private val tos: Boolean,
) : RootComponent.Configuration {
  override fun instanceKeeper(): InstanceKeeper.Instance {
    return PolicyTosComponent.Handler(tos)
  }

  override fun KoinFactory.get(context: ComponentContext): RootComponent.Child {
    return componentOf(::PolicyTosComponent, context)
  }
}

fun RootComponent.Configuration.Companion.policyTos(tos: Boolean): PolicyTosConfiguration {
  return PolicyTosConfiguration(tos)
}