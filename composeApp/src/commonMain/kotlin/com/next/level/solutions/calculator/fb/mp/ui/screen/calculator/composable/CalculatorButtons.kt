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
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory

@Composable
internal fun CalculatorButtons(
  creatingPassword: State<Boolean>,
  enteredNumberLength: State<Boolean>,
  modifier: Modifier = Modifier,
  space: Dp = 10.dp,
  action: (ButtonType) -> Unit,
) {
  Content(
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
    creatingPassword = creatingPassword,
    enteredNumberLength = enteredNumberLength,
    modifier = modifier,
    space = space,
  )
}

@Composable
private fun Content(
  creatingPassword: State<Boolean>,
  enteredNumberLength: State<Boolean>,
  modifier: Modifier = Modifier,
  space: Dp = 10.dp,
  action: (ButtonType) -> Unit = {},
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
      items = ButtonType.getButtons(),
      key = { it.toString() }
    ) { buttons ->
      Column(
        verticalArrangement = Arrangement.spacedBy(space = space),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
      ) {
        buttons.forEach { buttonType ->
          val creating = when {
            buttonType.operation -> creatingPassword
            else -> null
          }

          val enteredNumber = when {
            buttonType.operation -> enteredNumberLength
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
  type: ButtonType,
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

    type.isEqual() && enteredNumberLengthValue?.value == true -> MaterialTheme.colorScheme.primary

    else -> MaterialTheme.colorScheme.secondary
  }

  Text(
    text = type.text,
    color = MaterialTheme.colorScheme.onSecondary,
    style = when (type) {
      ButtonType.Delete -> TextStyleFactory.FS36.w400()
      else -> TextStyleFactory.FS32.w400()
    },
    modifier = modifier
      .fillMaxWidth()
      .let {
        when (type.operation) {
          false -> it.aspectRatio(ratio = 1f)
          true -> it.aspectRatio(ratio = 3 / 2.33f)
        }
      }
      .clip(shape = CircleShape)
      .background(color = backgroundColor)
      .clickable(onClick = action)
      .wrapContentSize()
  )
}