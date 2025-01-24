package com.next.level.solutions.calculator.fb.mp.ui.screen.onboarding

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
      withAd = withAd,
    )
}

@Composable
private fun Content(
  component: OnboardingComponent?,
  modifier: Modifier = Modifier,
  withAd: Boolean = true,
) {
  when (withAd/*remoteConfig?.adState?.native?.onboarding*/) {
    true -> ObContentWithAd(component = component, modifier = modifier)
    else -> ObContent(component = component, modifier = modifier)
  }
}