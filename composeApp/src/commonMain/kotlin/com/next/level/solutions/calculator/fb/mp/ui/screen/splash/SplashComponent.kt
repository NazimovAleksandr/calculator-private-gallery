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
import com.next.level.solutions.calculator.fb.mp.extensions.core.launchMain
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent
import com.next.level.solutions.calculator.fb.mp.ui.root.RootComponent.Configuration
import com.next.level.solutions.calculator.fb.mp.ui.root.calculator
import com.next.level.solutions.calculator.fb.mp.ui.root.language
import com.next.level.solutions.calculator.fb.mp.ui.root.onboarding
import com.next.level.solutions.calculator.fb.mp.utils.Logger
import kotlinx.coroutines.delay

class SplashComponent(
  componentContext: ComponentContext,
  private val adsManager: AdsManager,
  private val appDatastore: AppDatastore,
  private val navigation: StackNavigation<Configuration>,
) : RootComponent.Child(adsManager), ComponentContext by componentContext {

  init {
    Logger.w("TAG_SPLASH", "SplashComponent init = $this")
  }

  private val _progress: MutableValue<Float> by lazy { MutableValue(0f) }
  val progress: Value<Float> get() = _progress

  override fun content(): @Composable () -> Unit {
    launchMain { runProgress() }

    return {
      SplashContent(component = this)
    }
  }

  override fun action(action: Action) {}

  private suspend fun runProgress() {
    when {
      progress.value >= 1f -> adsManager.inter.show(::interOff)
//      progress.value > 0.7f && !consentState() -> incrementProgress(1000) // TODO
//      progress.value > 0.9f && !adState() -> incrementProgress(55) // TODO
      else -> incrementProgress(2)
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

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  class Handler : InstanceKeeper.Instance
}