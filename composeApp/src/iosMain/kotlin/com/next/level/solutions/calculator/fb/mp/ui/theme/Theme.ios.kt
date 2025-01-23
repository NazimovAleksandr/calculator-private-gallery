package com.next.level.solutions.calculator.fb.mp.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
actual fun AppTheme(
  darkTheme: Boolean,
  content: @Composable () -> Unit,
) {
  val colorScheme = when (darkTheme) {
    true -> DarkColorScheme
    else -> LightColorScheme
  }

  MaterialTheme(
    colors = colorScheme,
    shapes = MagicShapes,
    content = content,
  )
}