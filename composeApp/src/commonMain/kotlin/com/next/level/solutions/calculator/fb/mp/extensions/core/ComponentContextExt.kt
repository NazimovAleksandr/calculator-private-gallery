package com.next.level.solutions.calculator.fb.mp.extensions.core

import com.arkivanov.decompose.ComponentContext

inline fun <reified T> ComponentContext.instance(
  context: ComponentContext,
): T = instanceKeeper.getInstance(key = context) as T