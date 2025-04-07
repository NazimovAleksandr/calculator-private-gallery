package com.next.level.solutions.calculator.fb.mp.ui.screen.onboarding.composable

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.next
import calculator_privategallery.composeapp.generated.resources.skip
import com.next.level.solutions.calculator.fb.mp.ecosystem.ads.nativ.NativeSize
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.screen.onboarding.OnboardingComponent
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import com.next.level.solutions.calculator.fb.mp.utils.withNotNull
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ObContentWithAd(
  component: OnboardingComponent?,
  modifier: Modifier = Modifier,
) {
  Content(
    component = component,
    modifier = modifier,
  )
}

@Composable
private fun Content(
  component: OnboardingComponent?,
  modifier: Modifier = Modifier,
) {
  val pagerState = rememberPagerState(
    pageCount = { ObState.entries.size }
  )

  val scope = rememberCoroutineScope()

  val verticalGradient = Brush.verticalGradient(
    colorStops = listOf(
      0f to Color.Transparent,
      0.75f to MaterialTheme.colorScheme.background.copy(alpha = 0.89f),
      1f to MaterialTheme.colorScheme.background,
    ).toTypedArray()
  )

  Column(
    verticalArrangement = Arrangement.Bottom,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.background)
      .statusBarsPadding()
      .navigationBarsPadding()
  ) {
    Spacer(modifier = Modifier.height(height = 12.dp))

    Box(
      contentAlignment = Alignment.BottomCenter,
      modifier = Modifier
        .weight(weight = 1f)
    ) {
      HorizontalPager(
        state = pagerState,
        modifier = Modifier
          .fillMaxSize()
      ) {
        Card(
          state = ObState.entries[it],
          modifier = Modifier
            .fillMaxSize()
        )
      }

      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
          space = 10.dp,
          alignment = Alignment.CenterHorizontally,
        ),
        modifier = Modifier
          .fillMaxWidth()
          .background(brush = verticalGradient)
          .padding(vertical = 12.dp, horizontal = 16.dp)
      ) {
        Button(
          text = Res.string.skip,
          action = { component?.action(action = OnboardingComponent.Action.Skip) }
        )

        Spacer(modifier = Modifier.weight(weight = 1f))

        repeat(pagerState.pageCount) { iteration ->
          val color = when (pagerState.currentPage == iteration) {
            true -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            else -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
          }

          Box(
            modifier = Modifier
              .clip(shape = CircleShape)
              .background(color = color)
              .size(size = 8.dp)
          )
        }

        Spacer(modifier = Modifier.weight(weight = 1f))

        Button(
          text = Res.string.next,
          action = {
            when (pagerState.currentPage == pagerState.pageCount - 1) {
              true -> component?.action(action = OnboardingComponent.Action.Skip)

              else -> scope.launch {
                pagerState.animateScrollToPage(
                  page = pagerState.currentPage + 1,
                  animationSpec = tween(
                    durationMillis = 700,
                  )
                )
              }
            }
          }
        )
      }
    }

    withNotNull(component) {
      nativeAdCard(
        size = NativeSize.Adaptive,
        modifier = Modifier
          .weight(weight = 1f)
      )
    }
  }
}

@Composable
private fun Card(
  state: ObState,
  modifier: Modifier = Modifier,
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(space = 18.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
  ) {
    Text(
      text = stringResource(resource = state.title),
      style = TextStyleFactory.FS28.w700(),
      textAlign = TextAlign.Center,
      minLines = 2,
      modifier = Modifier
        .padding(horizontal = 50.dp)
    )

    Image(
      id = state.image,
      alignment = Alignment.TopCenter,
      contentScale = ContentScale.FillWidth,
      modifier = Modifier
        .weight(weight = 1f)
        .padding(horizontal = 50.dp)
    )
  }
}

@Composable
private fun Button(
  text: StringResource,
  action: () -> Unit,
) {
  Text(
    text = stringResource(resource = text),
    color = MaterialTheme.colorScheme.onSecondary,
    style = TextStyleFactory.FS15.w600(),
    modifier = Modifier
      .alpha(alpha = 0.3f)
      .padding(end = 16.dp)
      .clip(shape = CircleShape)
      .background(color = MaterialTheme.colorScheme.secondary)
      .clickable(onClick = action)
      .padding(vertical = 8.dp, horizontal = 16.dp)
  )
}