package com.next.level.solutions.calculator.fb.mp.ui.screen.lottie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import calculator_privategallery.composeapp.generated.resources.Res
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.ui.composable.back.handler.BackHandler
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi

@Composable
internal fun LottieContent(
  component: LottieComponent,
  modifier: Modifier = Modifier,
) {
  Content(
    component = component,
    modifier = modifier,
  )

  BackHandler(
    backHandler = component.backHandler,
    onBack = {},
  )
}

@Composable
fun LottieContentPreview(
  modifier: Modifier = Modifier
) {
  Content(
    component = null,
    modifier = modifier,
  )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun Content(
  component: LottieComponent?,
  modifier: Modifier = Modifier,
) {
  var speed by remember { mutableFloatStateOf(1f) }

  val dark = isSystemInDarkTheme()

  val composition by rememberLottieComposition {
    LottieCompositionSpec.JsonString(
      jsonString = Res.readBytes(
        path = when (dark) {
          true -> "files/lottie_hide_restore_night.json"
          else -> "files/lottie_hide_restore.json"
        }
      ).decodeToString()
    )
  }

  val progress by animateLottieCompositionAsState(
    composition = composition,
    speed = speed,
    iterations = 2,
  )

  if (progress == 1.0f) {
    component?.action(LottieComponent.Action.EndAnimation)
  }

  val model = component?.model?.subscribeAsState()

  val appLocked by remember {
    derivedStateOf {
      model?.value?.appLocked
    }
  }

  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
      .background(color = MaterialTheme.colorScheme.background)
      .navigationBarsPadding()
      .statusBarsPadding()
      .fillMaxSize()
      .pointerInput(key1 = Unit) {}
  ) {
    Image(
      painter = rememberLottiePainter(
        composition = composition,
        progress = { progress },
      ),
      contentDescription = null,
      contentScale = ContentScale.FillWidth,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 45.dp)
    )
  }

  LaunchedEffect(key1 = appLocked) {
    when (appLocked) {
      true -> if (progress < 1f) speed = 0f
      else -> if (progress < 1f) speed = 1f
    }
  }
}