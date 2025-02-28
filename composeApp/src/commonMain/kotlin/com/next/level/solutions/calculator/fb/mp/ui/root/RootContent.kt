package com.next.level.solutions.calculator.fb.mp.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.expect.AppEvent
import com.next.level.solutions.calculator.fb.mp.expect.setAppEventListeners
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RootContent(
  component: RootComponent,
  modifier: Modifier = Modifier,
) {
  Children(
    stack = component.stack,
    modifier = modifier,
    animation = stackAnimation(slide()),
    content = { it.instance.content() }
  )

  val scope = rememberCoroutineScope()

  LaunchedEffect(key1 = Unit) {
    setAppEventListeners {
      scope.launch(Dispatchers.Main) {
        when (it) {
          is AppEvent.AppOpen -> component.action(RootComponent.Action.AppOpen)
          is AppEvent.AppLock -> component.action(RootComponent.Action.LockOn)
        }
      }
    }
  }
}

@Composable
fun RootDialog(
  component: RootComponent,
) {
  val dialog by component.dialog.subscribeAsState()
  dialog.child?.instance?.content()
}