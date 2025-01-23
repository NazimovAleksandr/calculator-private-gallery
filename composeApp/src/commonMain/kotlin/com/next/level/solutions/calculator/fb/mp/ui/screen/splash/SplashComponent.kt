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
import com.next.level.solutions.calculator.fb.mp.ui.root.hiddenFiles
import kotlinx.coroutines.delay

class SplashComponent(
  componentContext: ComponentContext,
  private val adsManager: AdsManager,
  private val appDatastore: AppDatastore,
  private val navigation: StackNavigation<RootComponent.Configuration>,
) : RootComponent.Child, ComponentContext by componentContext {

  private val _progress: MutableValue<Float> = MutableValue(0f)
  val progress: Value<Float> = _progress

  override fun content(): @Composable () -> Unit {
    launchMain { runProgress() }

    return {
      SplashContent(component = this)
    }
  }

  fun interOff() {
//    analytics.splash.splashLoaded()

    launchMain {
      val configuration: RootComponent.Configuration = when {
        !appDatastore.policyStateOnce() -> RootComponent.Configuration.hiddenFiles() // TODO Onboarding
        !appDatastore.languageStateOnce() -> RootComponent.Configuration.hiddenFiles() // TODO Language()
        else -> RootComponent.Configuration.hiddenFiles() // TODO Calculator()
      }

      navigation.replaceCurrent(configuration)
    }
  }

  private suspend fun runProgress() {
    when {
      progress.value >= 1f -> adsManager.inter.show(::interOff)
//      progress.value > 0.7f && !consentState() -> incrementProgress(1000) // TODO
//      progress.value > 0.9f && !adState() -> incrementProgress(55) // TODO
      else -> incrementProgress(5)
    }
  }

  private suspend fun incrementProgress(delayTime: Long) {
    _progress.value += 0.001f

    delay(delayTime)
    runProgress()
  }

  /**
   * Component contract - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
   */
  companion object {
    val INSTANCE_KEY: String = this::class.toString()
  }

  class Handler : InstanceKeeper.Instance

}