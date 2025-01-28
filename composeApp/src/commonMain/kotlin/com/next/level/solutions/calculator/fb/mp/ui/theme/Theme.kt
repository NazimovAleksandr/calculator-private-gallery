package com.next.level.solutions.calculator.fb.mp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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
    colorScheme = colorScheme,
    shapes = MagicShapes,
    content = {
      Box(
        contentAlignment = Alignment.TopStart,
        content = { content() },
        modifier = Modifier.background(color = MaterialTheme.colorScheme.background)
      )
    },
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