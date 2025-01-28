package com.next.level.solutions.calculator.fb.mp.di

import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.stack.StackNavigation
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.Configuration
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.DialogConfiguration
import org.koin.dsl.module

val decomposeModule = module {
  single<StackNavigation<Configuration>> { StackNavigation() }
  single<SlotNavigation<DialogConfiguration>> { SlotNavigation() }
}