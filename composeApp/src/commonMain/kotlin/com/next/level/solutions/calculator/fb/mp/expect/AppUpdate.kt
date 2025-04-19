package com.next.level.solutions.calculator.fb.mp.expect

expect class AppUpdate {
  fun checkAppUpdate(type: String, result: (Boolean) -> Unit)
}