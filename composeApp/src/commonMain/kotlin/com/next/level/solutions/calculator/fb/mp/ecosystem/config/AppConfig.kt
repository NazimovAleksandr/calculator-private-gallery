package com.next.level.solutions.calculator.fb.mp.ecosystem.config

import com.next.level.solutions.calculator.fb.mp.ecosystem.analytics.AppAnalytics
import com.next.level.solutions.calculator.fb.mp.entity.config.AdsConfig
import com.next.level.solutions.calculator.fb.mp.entity.config.ApplicationConfig
import com.next.level.solutions.calculator.fb.mp.entity.config.SplashConfig
import com.next.level.solutions.calculator.fb.mp.expect.AppUpdate
import com.next.level.solutions.calculator.fb.mp.expect.PlatformExp
import com.next.level.solutions.calculator.fb.mp.utils.Logger
import com.next.level.solutions.calculator.fb.mp.utils.NetworkManager
import dev.gitlive.firebase.remoteconfig.FirebaseRemoteConfig
import dev.gitlive.firebase.remoteconfig.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds

class AppConfig(
  private val config: FirebaseRemoteConfig,
  private val networkManager: NetworkManager,
) {
  var app: ApplicationConfig = ApplicationConfig()
    private set

  var adsConfig: AdsConfig = AdsConfig()
    private set

  var splashConfig: SplashConfig = SplashConfig()
    private set

  fun init(onCompleted: () -> Unit) {
    networkManager.runAfterNetworkConnection(block = { fetch(onCompleted) })
  }

  private fun fetch(onCompleted: () -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
      try {
        config.settings {
          minimumFetchInterval = when (PlatformExp.isDebug) {
            true -> 10.seconds
            false -> 12.hours
          }
        }

        config.fetchAndActivate()
      } catch (_: Exception) {
      }

      adsConfig = formJson(config["ads"], ::AdsConfig)
      app = formJson(config["app"], ::ApplicationConfig)
      splashConfig = formJson(config["splash"], ::SplashConfig)

      Logger.d("AppConfig", "fetched")

      onCompleted()
    }
  }

  private inline fun <reified T> formJson(json: String?, constructor: () -> T): T {
    return try {
      json ?: throw Exception()
      Json.decodeFromString(json)
    } catch (_: Exception) {
      constructor()
    }
  }
}