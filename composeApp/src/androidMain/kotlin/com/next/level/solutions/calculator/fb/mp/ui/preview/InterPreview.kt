package com.next.level.solutions.calculator.fb.mp.ui.preview

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.next.level.solutions.calculator.fb.mp.ui.screen.ad.inter.InterDialogContentPreview
import com.next.level.solutions.calculator.fb.mp.ui.theme.AppThemePreview

@Preview(name = "Light", locale = "en")
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
  AppThemePreview {
    InterDialogContentPreview()
  }
}