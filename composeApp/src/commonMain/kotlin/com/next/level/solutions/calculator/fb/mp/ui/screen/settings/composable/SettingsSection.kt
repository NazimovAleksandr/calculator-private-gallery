package com.magiccalculatorlock.ui.screens.settings.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.next.level.solutions.calculator.fb.mp.entity.ui.extra.SettingsType
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun SettingsSection(
  item: SettingsType,
  modifier: Modifier = Modifier,
) {
  Text(
    text = stringResource(resource = item.title),
    style = TextStyleFactory.FS17.w400(),
    modifier = modifier
      .fillMaxWidth()
      .padding(all = 16.dp)
      .alpha(alpha = 0.6f)
  )
}