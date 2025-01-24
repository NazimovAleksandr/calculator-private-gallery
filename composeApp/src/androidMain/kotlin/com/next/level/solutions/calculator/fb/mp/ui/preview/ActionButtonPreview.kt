package com.next.level.solutions.calculator.fb.mp.ui.preview

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.next.level.solutions.calculator.fb.mp.ui.composable.action.button.ActionButtonPreview
import com.next.level.solutions.calculator.fb.mp.ui.icons.MagicIcons
import com.next.level.solutions.calculator.fb.mp.ui.icons.all.Plus
import com.next.level.solutions.calculator.fb.mp.ui.theme.AppThemePreview

@PreviewLightDark
@Composable
private fun Icon_Preview() {
  AppThemePreview {
    ActionButtonPreview(
      text = null,
      iconStart = MagicIcons.All.Plus,
      enabled = isSystemInDarkTheme(),
    )
  }
}

@PreviewLightDark
@Composable
private fun Text_Preview() {
  AppThemePreview {
    ActionButtonPreview(
      enabled = isSystemInDarkTheme(),
    )
  }
}

@PreviewLightDark
@Composable
private fun IconAndText_Row_Preview() {
  AppThemePreview {
    ActionButtonPreview(
      iconStart = MagicIcons.All.Plus,
      enabled = isSystemInDarkTheme(),
    )
  }
}

@PreviewLightDark
@Composable
private fun IconAndText_Column_Preview() {
  AppThemePreview {
    ActionButtonPreview(
      iconStart = MagicIcons.All.Plus,
      iconTop = MagicIcons.All.Plus,
      enabled = isSystemInDarkTheme(),
    )
  }
}