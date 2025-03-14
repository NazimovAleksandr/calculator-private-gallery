package com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.composable

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.expect.PlatformExp
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.buttons.Buttons
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.buttons.operation
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Composable
internal fun CalculatorButtons(
  buttons: ImmutableList<ImmutableList<Char>>,
  enteredNumberLength: State<Int>,
  modifier: Modifier = Modifier,
  space: Dp = 10.dp,
  action: (Char) -> Unit,
) {
  Content(
    buttons = buttons,
    enteredNumberLength = enteredNumberLength,
    modifier = modifier,
    action = action,
    space = space,
  )
}

@Composable
internal fun CalculatorButtonsPreview(
  modifier: Modifier = Modifier,
  space: Dp = 10.dp,
) {
  Content(
    buttons = Buttons().getButtons(),
    enteredNumberLength = remember { mutableStateOf(0) },
    modifier = modifier,
    space = space,
  )
}

@Composable
private fun Content(
  buttons: ImmutableList<ImmutableList<Char>>,
  enteredNumberLength: State<Int>,
  modifier: Modifier = Modifier,
  space: Dp = 10.dp,
  action: (Char) -> Unit = {},
) {
  val haptic = LocalHapticFeedback.current

  LazyVerticalGrid(
    verticalArrangement = Arrangement.spacedBy(space = space),
    horizontalArrangement = Arrangement.spacedBy(space = space),
    columns = GridCells.Fixed(4),
    userScrollEnabled = false,
    reverseLayout = true,
    modifier = modifier
  ) {
    items(
      items = buttons,
      key = { it.toString() }
    ) { buttons ->
      Column(
        verticalArrangement = Arrangement.spacedBy(space = space),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
      ) {
        buttons.forEach { buttonType ->
          val enteredNumberLengthState = when {
            buttonType == 'd' -> enteredNumberLength
            else -> null
          }

          Button(
            type = buttonType,
            enteredNumberLength = enteredNumberLengthState,
            action = {
              haptic.performHapticFeedback(it)
              action(buttonType)
            },
          )
        }
      }
    }
  }
}

@Composable
private fun Button(
  type: Char,
  enteredNumberLength: State<Int>?,
  modifier: Modifier = Modifier,
  action: (HapticFeedbackType) -> Unit,
) {
  val backgroundColor = when {
    type.operation() -> MaterialTheme.colorScheme.primary
    else -> MaterialTheme.colorScheme.secondary
  }

  val localIndication = LocalIndication.current
  val interactionSource = remember {
    MutableInteractionSource()
  }

  LaunchedEffect(key1 = interactionSource) {
    var longPressJob: Job? = null

    interactionSource.interactions.onEach { interaction: Interaction ->
      when (interaction) {
        is PressInteraction.Press -> {
          longPressJob?.cancel()
          longPressJob = launch {
            if (type == 'd') {
              delay(500)

              if (isActive) {
                launch {
                  while (isActive && (enteredNumberLength?.value ?: 0) > 0) {
                    action(HapticFeedbackType.LongPress)
                    delay(120)
                  }
                }
              }
            }
          }
        }

        is PressInteraction.Release -> {
          longPressJob?.cancel()
          longPressJob = null
        }

        is PressInteraction.Cancel -> {
          longPressJob?.cancel()
          longPressJob = null
        }
      }
    }
      .launchIn(this)
  }

  Text(
    text = type.text(),
    color = MaterialTheme.colorScheme.onSecondary,
    style = when {
      type.operation() -> TextStyleFactory.FS36.w400()
      else -> TextStyleFactory.FS32.w400()
    },
    modifier = modifier
      .fillMaxWidth()
      .let {
        when (type.operation()) {
          true -> it.aspectRatio(ratio = 3 / 2.33f)
          else -> it.aspectRatio(ratio = 1f)
        }
      }
      .clip(shape = CircleShape)
      .background(color = backgroundColor)
      .clickable(
        interactionSource = interactionSource,
        indication = localIndication,
        onClick = { action(HapticFeedbackType.TextHandleMove) },
      )
      .wrapContentSize()
      .let {
        when {
          type.operation() && PlatformExp.isIOS -> it.padding(bottom = 5.dp)
          else -> it
        }
      }
  )
}

private fun Char.text(): String {
  return when (this) {
    '*' -> "×"
    '/' -> "÷"
    'd' -> "⌫"
    else -> this.toString()
  }
}