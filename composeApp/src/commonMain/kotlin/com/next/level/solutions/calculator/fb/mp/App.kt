package com.next.level.solutions.calculator.fb.mp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.compose.LocalPlatformContext
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.di.dataModule
import com.next.level.solutions.calculator.fb.mp.di.decomposeModule
import com.next.level.solutions.calculator.fb.mp.di.ecosystemModule
import com.next.level.solutions.calculator.fb.mp.di.platformModule
import com.next.level.solutions.calculator.fb.mp.di.rootModule
import com.next.level.solutions.calculator.fb.mp.ui.root.rootComponent
import com.next.level.solutions.calculator.fb.mp.ui.theme.AppTheme
import com.next.level.solutions.calculator.fb.mp.utils.KoinFactory
import okio.FileSystem
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin
import org.koin.core.Koin
import org.koin.core.KoinApplication

@Composable
@Preview
fun App(
  componentContext: ComponentContext,
) {
  val context = LocalPlatformContext.current

  SingletonImageLoader.setSafe {
    ImageLoader.Builder(context)
      .crossfade(true)
      .memoryCachePolicy(CachePolicy.ENABLED)
      .memoryCache {
        MemoryCache.Builder()
          .maxSizePercent(context, 0.25)
          .build()
      }
      .diskCachePolicy(CachePolicy.ENABLED)
      .diskCache {
        DiskCache.Builder()
          .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
          .maxSizeBytes(512L * 1024 * 1024)
          .build()
      }
      .build()
  }

  Content(
    componentContext = componentContext,
  )
}

@Composable
private fun Content(
  componentContext: ComponentContext,
) {
  val koin: Koin = getKoin()

  val rootComponent = remember {
    koin.get<KoinFactory>().rootComponent(componentContext)
  }

  val roomModel by rootComponent.model.subscribeAsState()

  val darkTheme by remember {
    derivedStateOf {
      roomModel.darkTheme
    }
  }

  AppTheme(
    darkTheme = darkTheme,
    content = { rootComponent.content() },
  )
}

fun KoinApplication.appModules() {
  this.modules(
    modules = listOf(
      rootModule,
      platformModule,
      decomposeModule,
      ecosystemModule,
      dataModule,
    )
  )
}