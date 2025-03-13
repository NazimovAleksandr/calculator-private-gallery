package com.next.level.solutions.calculator.fb.mp.utils

expect class NetworkManager {
  fun runAfterNetworkConnection(block: () -> Unit)
}