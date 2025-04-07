package com.next.level.solutions.calculator.fb.mp.ui.screen.onboarding.composable

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.continue_
import calculator_privategallery.composeapp.generated.resources.skip
import com.next.level.solutions.calculator.fb.mp.constants.RESET_PASSWORD_CODE
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ActionButton
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.screen.onboarding.OnboardingComponent
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ObContent(
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

  Column(
    verticalArrangement = Arrangement.Bottom,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.background)
      .statusBarsPadding()
      .navigationBarsPadding()
  ) {
    Spacer(modifier = Modifier.height(height = 12.dp))

    ButtonSkip(
      action = { component?.action(action = OnboardingComponent.Action.Skip) }
    )

    Spacer(modifier = Modifier.height(height = 25.dp))

    HorizontalPager(
      state = pagerState,
      modifier = Modifier
        .weight(weight = 1f)
    ) {
      Card(
        state = ObState.entries[it],
        modifier = Modifier
          .fillMaxSize()
      )
    }

    Spacer(modifier = Modifier.height(height = 32.dp))

    ActionButton(
      text = stringResource(resource = Res.string.continue_),
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
      },
      modifier = Modifier
        .padding(horizontal = 50.dp)
        .fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(height = 20.dp))

    Row(
      horizontalArrangement = Arrangement.spacedBy(
        space = 10.dp,
        alignment = Alignment.CenterHorizontally,
      ),
      modifier = Modifier
        .fillMaxWidth()
    ) {
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
    }

    Spacer(modifier = Modifier.height(height = 12.dp))
  }
}

@Composable
private fun Card(
  state: ObState,
  modifier: Modifier = Modifier,
) {
  val verticalGradient = Brush.verticalGradient(
    colors = listOf(
      Color.Transparent,
      MaterialTheme.colorScheme.background,
      MaterialTheme.colorScheme.background,
    )
  )

  Box(
    contentAlignment = Alignment.BottomCenter,
    modifier = modifier
  ) {
    Image(
      id = state.image,
      modifier = Modifier
        .align(alignment = Alignment.TopCenter)
        .padding(horizontal = 40.dp)
    )

    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(height = 250.dp)
        .background(brush = verticalGradient)
    )

    Text(
      text = stringResource(resource = state.title).replace("%1\$s", RESET_PASSWORD_CODE),
      style = TextStyleFactory.FS28.w700(),
      textAlign = TextAlign.Center,
      modifier = Modifier
        .padding(horizontal = 50.dp)
    )
  }
}

@Composable
private fun ColumnScope.ButtonSkip(
  action: () -> Unit,
) {
  Text(
    text = stringResource(resource = Res.string.skip),
    color = MaterialTheme.colorScheme.onSecondary,
    style = TextStyleFactory.FS15.w600(),
    modifier = Modifier
      .alpha(alpha = 0.3f)
      .padding(end = 16.dp)
      .clip(shape = CircleShape)
      .background(color = MaterialTheme.colorScheme.secondary)
      .clickable(onClick = action)
      .padding(vertical = 8.dp, horizontal = 16.dp)
      .align(alignment = Alignment.End)
  )
}