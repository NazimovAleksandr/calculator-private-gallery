package com.next.level.solutions.calculator.fb.mp.ui.screen.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import calculator_fileblocking.composeapp.generated.resources.Res
import calculator_fileblocking.composeapp.generated.resources.app_name
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Calculator
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@Composable
fun SplashContent(
  component: SplashComponent,
  modifier: Modifier = Modifier,
) {
  Content(
    component = component,
    modifier = modifier,
  )
}

@Composable
fun SplashContentPreview(
  modifier: Modifier = Modifier,
) {
  Content(
    component = null,
    modifier = modifier,
  )
}

@Composable
private fun Content(
  component: SplashComponent?,
  modifier: Modifier = Modifier,
) {
  val progress = component?.progress?.subscribeAsState()

  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.background)
      .navigationBarsPadding()
      .statusBarsPadding()
      .fillMaxSize()
  ) {
    Spacer(modifier = Modifier.weight(weight = 28f))

    Image(
      vector = MagicIcons.All.Calculator,
      modifier = Modifier
        .size(size = 120.dp)
        .border(
          width = 1.dp,
          color = MaterialTheme.colorScheme.onBackground,
          shape = RoundedCornerShape(size = 30.dp)
        )
    )

    Spacer(modifier = Modifier.height(height = 24.dp))

    Text(
      text = stringResource(resource = Res.string.app_name).trim().replace(" - ", "\n"),
      style = TextStyleFactory.FS28.w400(),
      textAlign = TextAlign.Center,
      modifier = Modifier
        .padding(horizontal = 16.dp)
    )

    Spacer(modifier = Modifier.weight(weight = 30f))

    LinearProgressIndicator(
      progress = { progress?.value ?: 0.5f },
      color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
      trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
      strokeCap = StrokeCap.Round,
      gapSize = (-500).dp,
      drawStopIndicator = {},
      modifier = Modifier
        .width(width = 120.dp)
        .height(height = 8.dp)
    )

    Spacer(modifier = Modifier.height(height = 44.dp))

//    withNotNull(component) {
//      nativeAdCard(
//        size = NativeSize.Small,
//      )
//    }
  }
}