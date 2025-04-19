package com.next.level.solutions.calculator.fb.mp.ui.screen.splash

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.next.level.solutions.calculator.fb.mp.data.datastore.AppDatastore
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.AdsManager
import com.next.level.solutions.calculator.fb.mp.ecosystem.config.AppConfig
import com.next.level.solutions.calculator.fb.mp.expect.AppUpdate
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchIO
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.Configuration
import com.next.level.solutions.calculator.fb.mp.ui.root.calculator
import com.next.level.solutions.calculator.fb.mp.ui.root.language
import com.next.level.solutions.calculator.fb.mp.ui.root.onboarding
import kotlinx.coroutines.delay

class SplashComponent(
  componentContext: ComponentContext,
  private val adsManager: AdsManager,
  private val appDatastore: AppDatastore,
  private val navigation: StackNavigation<Configuration>,
  private val appConfig: AppConfig,
  private val appUpdate: AppUpdate,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  private val _progress: MutableValue<Float> by lazy { MutableValue(0f) }
  val progress: Value<Float> get() = _progress

  init {
    launchMain { runProgress() }

    launchIO {
      delay(300)
      appUpdate.checkAppUpdate(appConfig.app.appUpdateType) {

      }
    }
  }

  @Composable
  override fun content() {
    SplashContent(component = this)
  }

  override fun action(action: Action) {}

  private suspend fun runProgress() {
    when {
      progress.value >= 1f -> interOff()//adsManager.inter.show(::interOff)
      progress.value > 0.7f -> incrementProgress(incrementTime())
      progress.value > 0.9f -> incrementProgress(incrementTime())
      else -> incrementProgress(appConfig.splashConfig.incrementTime)
    }
  }

  private suspend fun incrementProgress(delayTime: Long) {
    _progress.value += 0.001f

    delay(delayTime)
    runProgress()
  }

  private fun interOff() {
//    analytics.splash.splashLoaded()

    launchMain {
      val configuration: Configuration = when {
        !appDatastore.policyStateOnce() -> Configuration.onboarding()
        !appDatastore.languageStateOnce() -> Configuration.language(changeMode = false)
        else -> Configuration.calculator(changeMode = false, password = appDatastore.passwordStateOnce())
      }

      navigation.replaceCurrent(configuration)
    }
  }

  private fun incrementTime(): Long = when {
    waitingAdOnSplash() && !adsManager.consentState() -> 1000
    waitingAdOnSplash() && !adsManager.inter.state().value -> 55
    else -> appConfig.splashConfig.incrementTime
  }

  private fun waitingAdOnSplash(): Boolean = appConfig.adsConfig.waitingOnSplash

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler : InstanceKeeper.Instance
}