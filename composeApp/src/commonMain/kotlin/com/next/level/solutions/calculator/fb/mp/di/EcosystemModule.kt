package com.next.level.solutions.calculator.fb.mp.di

import com.next.level.solutions.calculator.fb.mp.ecosystem.analytics.AppAnalytics
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.FirebaseAnalytics
import dev.gitlive.firebase.analytics.analytics
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val ecosystemModule = module {
  singleOf(::AppAnalytics)

  single<FirebaseAnalytics> {
    Firebase.analytics
  }
}