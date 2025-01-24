package com.next.level.solutions.calculator.fb.mp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
expect fun UpdateTheme(
  darkTheme: Boolean,
)

@Composable
fun AppTheme(
  darkTheme: Boolean,
  content: @Composable () -> Unit,
) {
  val colorScheme = when (darkTheme) {
    true -> DarkColorScheme
    else -> LightColorScheme
  }

  UpdateTheme(darkTheme)

  MaterialTheme(
    colors = colorScheme,
    shapes = MagicShapes,
    content = content,
  )
}

@Composable
fun AppThemePreview(
  content: @Composable () -> Unit,
) {
  AppTheme(
    darkTheme = isSystemInDarkTheme(),
    content = content,
  )
}