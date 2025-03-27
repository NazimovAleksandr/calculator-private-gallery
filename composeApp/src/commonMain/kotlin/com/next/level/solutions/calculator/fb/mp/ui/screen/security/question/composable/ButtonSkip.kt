package com.next.level.solutions.calculator.fb.mp.ui.screen.security.question.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import calculator_privategallery.composeapp.generated.resources.Res
import calculator_privategallery.composeapp.generated.resources.skip
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ButtonSkip(
  action: () -> Unit,
) {
  Text(
    text = stringResource(resource = Res.string.skip),
    color = MaterialTheme.colorScheme.onSecondary,
    style = TextStyleFactory.FS15.w600(),
    modifier = Modifier
      .clip(shape = CircleShape)
      .background(color = MaterialTheme.colorScheme.secondary)
      .clickable(onClick = action)
      .padding(vertical = 8.dp, horizontal = 16.dp)
  )
}