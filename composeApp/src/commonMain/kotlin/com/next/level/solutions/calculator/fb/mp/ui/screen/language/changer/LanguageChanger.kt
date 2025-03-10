package com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer

import com.next.level.solutions.calculator.fb.mp.ui.screen.language.model.LanguageModel

interface LanguageChanger {
  fun getDefaultLocaleLanguageCode(): String
  fun updateLocale(languageModel: LanguageModel)
}