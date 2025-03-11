package com.next.level.solutions.calculator.fb.mp.data.datastore.produce.path

import android.content.Context
import java.io.File

class ProducePathImpl(
  context: Context,
) : ProducePath {
  private val filesDir: File? = context.applicationContext.filesDir

  override fun getPath(name: String): String {
    return filesDir?.resolve(name)?.absolutePath.toString()
  }
}