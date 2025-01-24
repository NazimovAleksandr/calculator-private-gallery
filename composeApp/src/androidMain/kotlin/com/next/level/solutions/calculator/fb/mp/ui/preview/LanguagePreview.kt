package com.next.level.solutions.calculator.fb.mp.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.LanguageComponent
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.LanguageContentPreview
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.model.LanguageModel
import com.next.level.solutions.calculator.fb.mp.ui.theme.AppThemePreview
import kotlinx.collections.immutable.persistentListOf

@PreviewLightDark
@Composable
private fun Preview() {
  AppThemePreview {
    LanguageContentPreview(
      model = LanguageComponent.Model(
        languages = persistentListOf(
          listOf(
            LanguageModel("English", "en" ),
            LanguageModel("تعيين الرمز السري", "ar" ),
          ),
          listOf(
            LanguageModel("한국인", "ko" ),
            LanguageModel("Norsk", "nb" ),
          ),
          listOf(
            LanguageModel("Português", "pt" ),
            null,
          ),
        ),
        selected = LanguageModel("English", "en" ),
        activateCollapseSecurity = false,
      )
    )
  }
}