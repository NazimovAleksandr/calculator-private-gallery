package com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer

import com.next.level.solutions.calculator.fb.mp.ui.screen.language.model.LanguageModel
import kotlinx.collections.immutable.ImmutableList

interface LanguageChanger {
  fun selectedLocale(availableLanguages: MutableList<Pair<String, String>>): String
  fun languages(availableLanguages: MutableList<Pair<String, String>>,): ImmutableList<List<Pair<String, String>?>>
  fun updateLocale(languageModel: LanguageModel)
}

expect fun getLanguageChanger(): LanguageChanger

expect fun getDefaultLocaleLanguageCode(): String