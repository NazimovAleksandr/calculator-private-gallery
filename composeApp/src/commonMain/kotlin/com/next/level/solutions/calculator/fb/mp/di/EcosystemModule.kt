package com.next.level.solutions.calculator.fb.mp.di

import com.next.level.solutions.calculator.fb.mp.ecosystem.analytics.AppAnalytics
import com.next.level.solutions.calculator.fb.mp.ecosystem.config.AppConfig
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.analytics.FirebaseAnalytics
import dev.gitlive.firebase.analytics.analytics
import dev.gitlive.firebase.remoteconfig.FirebaseRemoteConfig
import dev.gitlive.firebase.remoteconfig.remoteConfig
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val ecosystemModule = module {
  singleOf(::AppAnalytics)
  singleOf(::AppConfig)

  single<FirebaseAnalytics> {
    Firebase.analytics
  }

  single<FirebaseRemoteConfig> {
    Firebase.remoteConfig
  }
}