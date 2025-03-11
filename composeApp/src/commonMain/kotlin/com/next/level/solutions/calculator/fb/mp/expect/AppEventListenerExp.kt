package com.next.level.solutions.calculator.fb.mp.expect

sealed interface AppEvent{
  object AppOpen: AppEvent
  object AppLock: AppEvent
}

expect class AppEventListener {
  fun set(callback: (AppEvent) -> Unit)
}