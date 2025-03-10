package com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.buttons.Buttons
import com.next.level.solutions.calculator.fb.mp.ui.screen.calculator.buttons.operation
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun CalculatorButtons(
  buttons: ImmutableList<ImmutableList<Char>>,
  creatingPassword: State<Boolean>,
  enteredNumberLength: State<Boolean>,
  modifier: Modifier = Modifier,
  space: Dp = 10.dp,
  action: (Char) -> Unit,
) {
  Content(
    buttons = buttons,
    creatingPassword = creatingPassword,
    enteredNumberLength = enteredNumberLength,
    modifier = modifier,
    action = action,
    space = space,
  )
}

@Composable
internal fun CalculatorButtonsPreview(
  creatingPassword: State<Boolean>,
  enteredNumberLength: State<Boolean>,
  modifier: Modifier = Modifier,
  space: Dp = 10.dp,
) {
  Content(
    buttons = Buttons().getButtons(),
    creatingPassword = creatingPassword,
    enteredNumberLength = enteredNumberLength,
    modifier = modifier,
    space = space,
  )
}

@Composable
private fun Content(
  buttons: ImmutableList<ImmutableList<Char>>,
  creatingPassword: State<Boolean>,
  enteredNumberLength: State<Boolean>,
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
          val creating = when {
            buttonType.operation() -> creatingPassword
            else -> null
          }

          val enteredNumber = when {
            buttonType.operation() -> enteredNumberLength
            else -> null
          }

          Button(
            type = buttonType,
            creatingPassword = creating,
            enteredNumberLength = enteredNumber,
            action = {
              haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
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
  creatingPassword: State<Boolean>?,
  enteredNumberLength: State<Boolean>?,
  modifier: Modifier = Modifier,
  action: () -> Unit,
) {
  val creatingPasswordValue = remember(creatingPassword) {
    creatingPassword
  }

  val enteredNumberLengthValue = remember(enteredNumberLength) {
    enteredNumberLength
  }

  val backgroundColor = when {
    creatingPasswordValue?.value == false -> MaterialTheme.colorScheme.primary
    type == '=' && enteredNumberLengthValue?.value == true -> MaterialTheme.colorScheme.primary
    else -> MaterialTheme.colorScheme.secondary
  }

  Text(
    text = type.text(),
    color = MaterialTheme.colorScheme.onSecondary,
    style = when {
      type == '/' -> TextStyleFactory.FS36.w400()
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
      .clickable(onClick = action)
      .wrapContentSize()
  )
}

private fun Char.text(): String {
  return when (this) {
    '*' -> "×"
    '/' -> "÷"
    'd' -> "   ⌫    "
    else -> this.toString()
  }
}