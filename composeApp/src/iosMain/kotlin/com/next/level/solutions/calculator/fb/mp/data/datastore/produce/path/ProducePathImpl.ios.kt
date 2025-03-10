package com.next.level.solutions.calculator.fb.mp.data.datastore.produce.path

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

class ProducePathImpl : ProducePath {
  @OptIn(ExperimentalForeignApi::class)
  override fun getPath(name: String): String {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
      directory = NSDocumentDirectory,
      inDomain = NSUserDomainMask,
      appropriateForURL = null,
      create = false,
      error = null,
    )

    return requireNotNull(documentDirectory).path + "/$name"
  }
}