package com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer

import com.next.level.solutions.calculator.fb.mp.ui.screen.language.model.LanguageModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

actual fun getLanguageChanger(): LanguageChanger {
  return LanguageChangerImpl()
}

actual fun getDefaultLocaleLanguageCode(): String {
  return LanguageManager.defaultLocale() ?: "en"
}

class LanguageChangerImpl : LanguageChanger { // TODO: Not working
  override fun selectedLocale(availableLanguages: MutableList<Pair<String, String>>): String {
    return ""
  }

  override fun languages(availableLanguages: MutableList<Pair<String, String>>): ImmutableList<List<Pair<String, String>?>> {
    val language = LanguageManager.defaultLocale()

    return availableLanguages
      .let { list ->
        val currentLocal = list.firstOrNull { pair ->
          pair.first == language
        }

        when {
          currentLocal != null -> {
            list.remove(currentLocal)
            list.add(1, currentLocal)
          }
        }

        val resultList: MutableList<List<Pair<String, String>?>> = mutableListOf()

        for (i in list.indices step 2) {
          resultList.add(
            listOf(
              list.getOrNull(i),
              list.getOrNull(i + 1),
            )
          )
        }

        resultList
      }
      .toImmutableList()
  }

  override fun updateLocale(languageModel: LanguageModel) {
    LanguageManager.updateLocale(languageModel.code)
  }
}