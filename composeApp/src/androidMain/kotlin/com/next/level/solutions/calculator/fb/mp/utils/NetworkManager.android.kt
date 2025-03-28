package com.next.level.solutions.calculator.fb.mp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.core.content.getSystemService

actual class NetworkManager(context: Context) : ConnectivityManager.NetworkCallback() {

  private val connectivityManager: ConnectivityManager? = context.getSystemService()
  private var onAvailableCallback: (() -> Unit)? = null

  actual fun runAfterNetworkConnection(block: () -> Unit) {
    onAvailableCallback = block
    connectivityManager?.registerDefaultNetworkCallback(this)
  }

  override fun onAvailable(network: Network) {
    super.onAvailable(network)
    onAvailableCallback?.invoke()
    onAvailableCallback = null
    connectivityManager?.unregisterNetworkCallback(this)
  }
}