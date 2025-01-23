package com.next.level.solutions.calculator.fb.mp.di

import com.arkivanov.decompose.router.stack.StackNavigation
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.Configuration
import org.koin.dsl.module

val decomposeModule = module {
  single<StackNavigation<Configuration>> { StackNavigation() }
}