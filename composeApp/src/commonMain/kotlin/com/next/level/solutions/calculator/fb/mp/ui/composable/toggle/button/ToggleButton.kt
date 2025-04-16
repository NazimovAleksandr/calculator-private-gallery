package com.next.level.solutions.calculator.fb.mp.ui.composable.toggle.button

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun ToggleButton(
  initChecked: Boolean,
  thumb: @Composable BoxScope.() -> Unit,
  modifier: Modifier = Modifier,
  onCheckedChange: (Boolean) -> Unit = {},
) {
  var checked by remember { mutableStateOf(initChecked) }
  val interactionSource = remember { MutableInteractionSource() }

  val density = LocalDensity.current

  var thumbOffset by remember {
    mutableFloatStateOf(0f)
  }

  val thumbPosition by animateFloatAsState(
    targetValue = if (checked) 1f else 0f
  )

  fun calculateThumbOffset(
    start: Float,
    stop: Float,
    fraction: Float
  ): Float = start + (stop - start) * fraction

  Box(
    contentAlignment = Alignment.CenterStart,
    modifier = modifier
      .size(width = 70.dp, height = 30.dp)
      .clip(shape = CircleShape)
      .background(color = MaterialTheme.colorScheme.surfaceContainer)
      .clickable(
        onClick = {
          onCheckedChange(!checked)
          checked = !checked
        },
        interactionSource = interactionSource,
        indication = null
      )
      .drawWithContent {
        thumbOffset = calculateThumbOffset(
          start = with(density) { 4.dp.toPx() },
          stop = size.width - with(density) { 34.dp.toPx() },
          fraction = thumbPosition
        )

        drawContent()
      }
  ) {
    Box(
      content = thumb,
      modifier = Modifier
        .offset { IntOffset(x = thumbOffset.roundToInt(), y = 0) }
    )
  }
}