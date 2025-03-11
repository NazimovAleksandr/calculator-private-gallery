package com.next.level.solutions.calculator.fb.mp.ecosystem.ads.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.getSystemService

val Context.connectivityManager: ConnectivityManager?
  get() = getSystemService<ConnectivityManager>()

val ConnectivityManager.isNetworkConnected: Boolean
  @Suppress("DEPRECATION")
  get() = activeNetworkInfo?.isConnected == true