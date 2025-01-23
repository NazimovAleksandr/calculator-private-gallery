package com.next.level.solutions.calculator.fb.mp.di

import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.adsManager
import org.koin.dsl.module

val ecosystemModule = module {
  single { adsManager() }
}