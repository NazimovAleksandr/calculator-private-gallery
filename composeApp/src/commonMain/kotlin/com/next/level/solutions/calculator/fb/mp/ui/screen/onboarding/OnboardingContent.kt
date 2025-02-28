package com.next.level.solutions.calculator.fb.mp.ui.screen.onboarding

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.ui.screen.onboarding.composable.ObContent
import com.next.level.solutions.calculator.fb.mp.ui.screen.onboarding.composable.ObContentWithAd

@Composable
fun OnboardingContent(
  component: OnboardingComponent,
  modifier: Modifier = Modifier,
) {
  Content(
    component = component,
    modifier = modifier,
  )
}

@Composable
fun OnboardingContentPreview(
  modifier: Modifier = Modifier,
  withAd: Boolean = isSystemInDarkTheme(),
) {
    Content(
      component = null,
      modifier = modifier,
      withAdPreview = withAd,
    )
}

@Composable
private fun Content(
  component: OnboardingComponent?,
  modifier: Modifier = Modifier,
  withAdPreview: Boolean? = null,
) {
  val model = component?.model?.subscribeAsState()

  val withAd by remember {
    derivedStateOf {
      withAdPreview ?: model?.value?.withAd ?: false
    }
  }

  when (withAd) {
    true -> ObContentWithAd(component = component, modifier = modifier)
    else -> ObContent(component = component, modifier = modifier)
  }
}