package com.next.level.solutions.calculator.fb.mp.ui.screen.security.question.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.your_answer
import com.next.level.solutions.calculator.fb.mp.ui.composable.text.field.AppTextField

@Composable
internal inline fun ColumnScope.Answer(
  initAnswer: String?,
  crossinline action: (String) -> Unit,
) {
  val density = LocalDensity.current

  val minHeight = remember { 68.dp }

  val minHeightForAnswer = remember {
    with(density) { (minHeight + 2.dp).toPx() }
  }

  val minHeightModifier = remember {
    Modifier
      .height(height = minHeight)
  }

  val weightModifier = remember {
    Modifier
      .weight(weight = 1f)
  }

  var spacerModifier by remember {
    mutableStateOf(
      Modifier
        .height(0.dp)
    )
  }

  var answerModifier by remember {
    mutableStateOf(weightModifier)
  }

  Box(
    contentAlignment = Alignment.TopStart,
    modifier = answerModifier
      .onGloballyPositioned {
        if (it.size.height < minHeightForAnswer) {
          answerModifier = minHeightModifier
          spacerModifier = weightModifier
        }
      }
  ) {
    AppTextField(
      initValue = initAnswer ?: "",
      placeholder = Res.string.your_answer,
      onValueChange = { action(it) },
      modifier = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth()
    )
  }

  Spacer(
    modifier = spacerModifier
      .onGloballyPositioned {
        if (it.size.height > 0) {
          answerModifier = weightModifier
          spacerModifier = Modifier
        }
      }
  )
}