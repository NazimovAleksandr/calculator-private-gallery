package com.next.level.solutions.calculator.fb.mp.utils

expect class NetworkManager {
  fun runAfterNetworkConnection(@Suppress("unused") block: () -> Unit)
}