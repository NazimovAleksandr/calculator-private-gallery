package com.next.level.solutions.calculator.fb.mp.ui.composable.lifecycle.event.listener

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
inline fun LifecycleEventListener(
  lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
  crossinline onCreate: () -> Unit = {},
  crossinline onStart: () -> Unit = {},
  crossinline onResume: () -> Unit = {},
  crossinline onPause: () -> Unit = {},
  crossinline onStop: () -> Unit = {},
  crossinline onDestroy: () -> Unit = {},
  crossinline onAny: () -> Unit = {},
  crossinline onDispose: () -> Unit = {},
) {
  val currentOnCreate by rememberUpdatedState { onCreate.invoke() }
  val currentOnStart by rememberUpdatedState { onStart.invoke() }
  val currentOnResume by rememberUpdatedState { onResume.invoke() }
  val currentOnPause by rememberUpdatedState { onPause.invoke() }
  val currentOnStop by rememberUpdatedState { onStop.invoke() }
  val currentOnDestroy by rememberUpdatedState { onDestroy.invoke() }
  val currentOnAny by rememberUpdatedState { onAny.invoke() }
  val currentOnDispose by rememberUpdatedState { onDispose.invoke() }

  DisposableEffect(lifecycleOwner) {
    val observer = LifecycleEventObserver { _, event ->
      when (event) {
        Lifecycle.Event.ON_CREATE -> currentOnCreate()
        Lifecycle.Event.ON_START -> currentOnStart()
        Lifecycle.Event.ON_RESUME -> currentOnResume()
        Lifecycle.Event.ON_PAUSE -> currentOnPause()
        Lifecycle.Event.ON_STOP -> currentOnStop()
        Lifecycle.Event.ON_DESTROY -> currentOnDestroy()
        Lifecycle.Event.ON_ANY -> currentOnAny()
      }
    }

    lifecycleOwner.lifecycle.addObserver(observer)

    onDispose {
      currentOnDispose()
      lifecycleOwner.lifecycle.removeObserver(observer)
    }
  }
}