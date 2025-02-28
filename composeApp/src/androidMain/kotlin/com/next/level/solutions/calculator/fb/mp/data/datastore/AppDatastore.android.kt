package com.next.level.solutions.calculator.fb.mp.data.datastore

import com.next.level.solutions.calculator.fb.mp.MainActivity

actual fun producePath(name: String): String {
  return MainActivity.expect?.value?.producePath?.invoke(name) ?: throw IllegalStateException("MainActivity.producePath is null")
}