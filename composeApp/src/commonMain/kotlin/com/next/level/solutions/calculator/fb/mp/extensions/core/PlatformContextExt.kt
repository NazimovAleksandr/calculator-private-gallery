package com.next.level.solutions.calculator.fb.mp.extensions.core

import coil3.PlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.toBitmap
import com.next.level.solutions.calculator.fb.mp.expect.PlatformExp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class CacheParams(
  val key: String,
  val fileQuality: Int = 100,
  val scope: CoroutineScope,
)

fun PlatformContext.imageRequest(
  data: Any?,
  cacheParams: CacheParams? = null,
): ImageRequest = ImageRequest.Builder(this)
  .data(data)
  .diskCachePolicy(CachePolicy.ENABLED)
  .diskCacheKey(cacheParams?.key)
  .crossfade(true)
  .listener { _, result ->
    cacheParams?.apply {
      scope.launch(Dispatchers.IO) {
        PlatformExp.saveToCache(
          icon = result.image.toBitmap(),
          name = key,
          quality = fileQuality,
        )
      }
    }
  }
  .build()