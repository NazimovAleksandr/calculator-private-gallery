package com.next.level.solutions.calculator.fb.mp.ui.composable.reminder.card

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ReminderCard(
  title: StringResource,
  description: String,
  modifier: Modifier = Modifier,
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(space = 5.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .fillMaxWidth()
      .border(
        width = 1.dp,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.extraLarge,
      )
      .padding(vertical = 32.dp, horizontal = 24.dp)
  ) {
    val annotatedString = buildAnnotatedString {
      withStyle(
        style = SpanStyle(color = MaterialTheme.colorScheme.primary),
        block = { append(text = "*") }
      )

      append(text = stringResource(resource = title))
    }

    Text(
      text = annotatedString,
      style = TextStyleFactory.FS17.w400(),
      textAlign = TextAlign.Center,
      modifier = Modifier
        .alpha(alpha = 0.7f)
    )

    Text(
      text = description,
      style = TextStyleFactory.FS28.w700(),
      textAlign = TextAlign.Center,
      modifier = Modifier
    )
  }
}