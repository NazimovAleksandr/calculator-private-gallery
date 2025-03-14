package com.next.level.solutions.calculator.fb.mp.ecosystem.config

import com.next.level.solutions.calculator.fb.mp.entity.config.AdsConfig
import com.next.level.solutions.calculator.fb.mp.entity.config.SplashConfig
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
  var adsConfig: AdsConfig = AdsConfig()
    private set

  var splashConfig: SplashConfig = SplashConfig()
    private set

  fun init() {
    networkManager.runAfterNetworkConnection(block = ::fetch)
  }

  private fun fetch() {
    CoroutineScope(Dispatchers.IO).launch {
      config.settings {
        minimumFetchInterval = when (PlatformExp.isDebug) {
          true -> 10.seconds
          false -> 12.hours
        }
      }

      config.fetchAndActivate()

      adsConfig = Json.decodeFromString(config["ads"])
      splashConfig = Json.decodeFromString(config["splash"])

      Logger.d("AppConfig", "fetched")
    }
  }
}