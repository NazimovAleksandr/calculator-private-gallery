package com.next.level.solutions.calculator.fb.mp.extensions.core

import com.arkivanov.decompose.ComponentContext
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent

inline fun <reified T> ComponentContext.instance(
  context: ComponentContext,
): T = instanceKeeper.getInstance(key = context) as T

inline fun ComponentContext.getRootComponent(): RootComponent = instanceKeeper.getInstance(
  key = "RootComponent",
) as? RootComponent ?: throw IllegalStateException("RootComponent not found")