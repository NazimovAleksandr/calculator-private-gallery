package com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer

import com.next.level.solutions.calculator.fb.mp.ui.screen.language.model.LanguageModel
import platform.Foundation.NSUserDefaults

class LanguageChangerImpl : LanguageChanger {
  override fun getDefaultLocaleLanguageCode(): String {
    return NSUserDefaults.standardUserDefaults.objectForKey("AppleLanguages").let {
      (it as? List<*>)?.firstOrNull()?.toString()?.substringBefore("-") ?: "en"
    }
  }

  override fun updateLocale(languageModel: LanguageModel) {
    NSUserDefaults.standardUserDefaults.setObject(
      value = arrayListOf(languageModel.code + "-" + languageModel.country),
      forKey = "AppleLanguages",
    )
  }
}