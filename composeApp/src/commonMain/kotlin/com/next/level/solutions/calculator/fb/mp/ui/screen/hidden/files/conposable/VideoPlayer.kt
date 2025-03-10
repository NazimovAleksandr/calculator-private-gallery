package com.next.level.solutions.calculator.fb.mp.ui.screen.hidden.files.conposable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.next.level.solutions.calculator.fb.mp.entity.ui.FileDataUI

sealed interface VideoPlayerAction {
  data class LandscapeChange(val state: Boolean) : VideoPlayerAction
  data class FullScreenChange(val state: Boolean) : VideoPlayerAction
}

@Composable
expect fun VideoPlayer(
  file: FileDataUI,
  modifier: Modifier = Modifier,
  action: (VideoPlayerAction) -> Unit,
)