package com.next.level.solutions.calculator.fb.mp.di

import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import org.koin.dsl.module

val rootModule = module {
  factory<KoinFactory> { KoinFactory(::get) }
}