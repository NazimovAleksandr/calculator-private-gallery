package com.next.level.solutions.calculator.fb.mp.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kotlinx.coroutines.delay

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

  LaunchedEffect(key1 = Unit) {
    delay(200)
    component.action(RootComponent.Action.InitAppConfig)
  }
}

@Composable
fun RootDialog(
  component: RootComponent,
) {
  val dialog by component.dialog.subscribeAsState()
  dialog.child?.instance?.content()
}