package com.next.level.solutions.calculator.fb.mp.data.datastore.produce.path

import java.io.File

class ProducePathImpl(
  private val filesDir: File,
) : ProducePath {
  override fun getPath(name: String): String {
    return filesDir.resolve(name).absolutePath
  }
}