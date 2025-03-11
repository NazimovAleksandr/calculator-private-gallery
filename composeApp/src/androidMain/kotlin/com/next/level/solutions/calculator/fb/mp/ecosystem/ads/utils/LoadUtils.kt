package com.next.level.solutions.calculator.fb.mp.ecosystem.ads.utils

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun Context.loadAd(
  load: () -> Unit,
) {
  if (connectivityManager?.isNetworkConnected == true) {
    load()
  } else {
    CoroutineScope(Dispatchers.Main).launch {
      while (connectivityManager?.isNetworkConnected != true) {
        delay(5000)
      }

      load()
    }
  }
}