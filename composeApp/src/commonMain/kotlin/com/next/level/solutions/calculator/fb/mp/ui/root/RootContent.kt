package com.next.level.solutions.calculator.fb.mp.ui.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation

@Composable
fun RootContent(
  component: RootComponent,
  modifier: Modifier = Modifier,
) {
  Children(
    stack = component.stack,
    modifier = modifier,
    animation = stackAnimation(slide()),
    content = { it.instance.toContent().invoke(it.instance) }
  )
}