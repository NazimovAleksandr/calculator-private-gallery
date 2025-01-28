@file:Suppress("PackageDirectoryMismatch")

package com.next.level.solutions.calculator.fb.mp.ui.root

import com.arkivanov.decompose.ComponentContext
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory

fun KoinFactory.rootComponent(context: ComponentContext): RootComponent {
  return componentOf(::RootComponent, context)
}