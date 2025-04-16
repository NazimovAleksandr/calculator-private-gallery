package com.next.level.solutions.calculator.fb.mp.ui.screen.settings.composable

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.next.level.solutions.calculator.fb.mp.entity.ui.extra.SettingsModelUI
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ActionButton
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ButtonColors
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ContentSpace
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.IconSize
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.IconType
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.ArrowForward
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Language
import com.next.level.solutions.calculator.fb.mp.ui.screen.settings.SettingsComponent
import com.next.level.solutions.calculator.fb.mp.ui.theme.TextStyleFactory
import org.jetbrains.compose.resources.stringResource

@Composable
fun ColumnScope.SettingsItemButton(
  component: SettingsComponent?,
  settingsModelUI: SettingsModelUI,
  modifier: Modifier = Modifier
) {
  val model = component?.model?.subscribeAsState()

  val language = model?.value?.language?.collectAsState("")

  val icon = settingsModelUI.icon

  val text = when (icon) {
    MagicIcons.All.Language -> language?.value.toString()
    else -> stringResource(resource = settingsModelUI.title)
  }

  ActionButton(
    iconStart = icon,
    iconStartSize = IconSize(height = icon.defaultHeight, width = icon.defaultWidth),
    text = text,
    iconEnd = MagicIcons.All.ArrowForward,
    iconEndSize = IconSize(size = 48.dp),
    iconType = IconType.Icon,
    style = TextStyleFactory.FS17.w400(),
    contentSpace = ContentSpace(horizontal = 10.dp),
    paddingValues = PaddingValues(start = 16.dp, top = 10.dp, end = 4.dp, bottom = 10.dp),
    colors = ButtonColors.default(
      containerColor = MaterialTheme.colorScheme.secondary,
      contentColor = MaterialTheme.colorScheme.onSecondary,
      iconColor = MaterialTheme.colorScheme.primary,
    ),
    action = { component?.action(SettingsComponent.Action.Item(settingsModelUI)) },
    textModifier = Modifier.weight(weight = 1f),
    modifier = modifier.fillMaxWidth(),
  )
}