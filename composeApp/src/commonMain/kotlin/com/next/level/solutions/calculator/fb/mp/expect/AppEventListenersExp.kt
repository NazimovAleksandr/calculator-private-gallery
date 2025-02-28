package com.next.level.solutions.calculator.fb.mp.expect

sealed interface AppEvent{
  object AppOpen: AppEvent
  object AppLock: AppEvent
}

expect fun setAppEventListeners(callback: (AppEvent) -> Unit)