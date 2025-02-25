package com.next.level.solutions.calculator.fb.mp.ui.composable.back.handler

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.backhandler.BackHandler

@Composable
fun BackHandler(
  backHandler: BackHandler,
  isEnabled: Boolean = true,
  onBack: () -> Unit
) {
  val currentOnBack by rememberUpdatedState(onBack)

  val callback = remember {
    BackCallback(isEnabled = isEnabled) {
      currentOnBack()
    }
  }

  SideEffect { callback.isEnabled = isEnabled }

  DisposableEffect(key1 = backHandler) {
    backHandler.register(callback)
    onDispose { backHandler.unregister(callback) }
  }
}