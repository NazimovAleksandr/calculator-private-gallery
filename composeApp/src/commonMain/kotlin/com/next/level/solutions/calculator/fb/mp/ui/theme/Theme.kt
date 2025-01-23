package com.next.level.solutions.calculator.fb.mp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

@Composable
expect fun AppTheme(
  darkTheme: Boolean,
  content: @Composable () -> Unit,
)

@Composable
fun AppThemePreview(
  content: @Composable () -> Unit,
) {
  AppTheme(
    darkTheme = isSystemInDarkTheme(),
    content = content,
  )
}