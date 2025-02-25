package com.next.level.solutions.calculator.fb.mp.ui.screen.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.ui.composable.image.Image
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RowScope.RowCard(
  icon: ImageVector,
  text: StringResource,
  action: () -> Unit,
) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .weight(weight = 1f)
      .aspectRatio(ratio = 1f)
      .clip(shape = MaterialTheme.shapes.extraLarge)
      .background(color = MaterialTheme.colorScheme.secondaryContainer)
      .clickable(onClick = action)
      .padding(horizontal = 8.dp)
  ) {
    Spacer(modifier = Modifier.weight(weight = 30f))

    Image(
      vector = icon,
      modifier = Modifier
    )

    Spacer(modifier = Modifier.weight(weight = 16f))

    Text(
      text = stringResource(resource = text),
      color = MaterialTheme.colorScheme.onSecondaryContainer,
      style = TextStyleFactory.FS16.w600(),
      overflow = TextOverflow.Ellipsis,
      textAlign = TextAlign.Center,
      maxLines = 1,
      modifier = Modifier
    )

    Spacer(modifier = Modifier.weight(weight = 22f))
  }
}