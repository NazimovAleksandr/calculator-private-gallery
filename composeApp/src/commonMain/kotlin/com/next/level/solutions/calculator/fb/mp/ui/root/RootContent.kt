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
  val dialog by component.dialog.subscribeAsState()

  Children(
    stack = component.stack,
    modifier = modifier,
    animation = stackAnimation(slide()),
    content = { it.instance.content().invoke() }
  )

  dialog.child?.instance?.content()?.invoke()

  val scope = rememberCoroutineScope()

  LaunchedEffect(key1 = Unit) {
    setAppEventListeners {
      scope.launch(Dispatchers.Main) {
        when (it) {
          is AppEvent.AppOpen -> {} // todo
          is AppEvent.AppLock -> component.action(RootComponent.Action.LockOn)
        }
      }
    }
  }
}