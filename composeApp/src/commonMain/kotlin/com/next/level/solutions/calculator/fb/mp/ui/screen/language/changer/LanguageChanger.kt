package com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer

import com.next.level.solutions.calculator.fb.mp.ui.screen.language.model.LanguageModel

interface LanguageChanger {
  fun updateLocale(languageModel: LanguageModel)
}

expect fun getLanguageChanger(): LanguageChanger

expect fun getDefaultLocaleLanguageCode(): String