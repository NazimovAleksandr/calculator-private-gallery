package com.next.level.solutions.calculator.fb.mp.ui.screen.browser.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebViewState

@Composable
internal fun PageLoadingBar(
  webViewState: WebViewState,
  modifier: Modifier = Modifier,
) {
  val loadingState by remember {
    derivedStateOf { webViewState.loadingState }
  }

  var progressValue by remember {
    mutableStateOf(0f)
  }

  LinearProgressIndicator(
    progress = { progressValue },
    color = MaterialTheme.colorScheme.onPrimary,
    trackColor = Color.Transparent,
    drawStopIndicator = {},
    modifier = modifier
      .offset(y = (-1).dp)
      .fillMaxWidth()
      .height(height = 2.5.dp)
  )

  LaunchedEffect(key1 = loadingState) {
    progressValue = when (loadingState) {
      is LoadingState.Loading -> (loadingState as LoadingState.Loading).progress
      else -> 0f
    }
  }
}