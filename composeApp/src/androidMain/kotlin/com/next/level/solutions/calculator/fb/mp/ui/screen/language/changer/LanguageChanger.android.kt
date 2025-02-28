package com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
import com.next.level.solutions.calculator.fb.mp.MainActivity
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.model.LanguageModel
import java.util.Locale

actual fun getLanguageChanger(): LanguageChanger {
  return MainActivity.expect?.value?.languageChanger?.value ?: throw IllegalStateException("LanguageChanger is not initialized")
}

actual fun getDefaultLocaleLanguageCode(): String {
  return Locale.getDefault().language ?: "en"
}

class LanguageChangerImpl(
  private val activity: Activity,
  private val store: ChangerLocalStore,
) : LanguageChanger {

  init {
    activity.updateLocale(store.getLocale())
  }

  override fun updateLocale(languageModel: LanguageModel) {
    store.persistLocale(languageModel.toLocale())
    activity.updateLocale(languageModel.toLocale())
//    activity.recreate()
  }

  private fun LanguageModel.toLocale(): Locale {
    return Locale(code, country.replace("-", ""), variant)
  }

  private fun Context.updateLocale(locale: Locale) {
    updateResources(locale)

    val appContext = applicationContext

    if (appContext !== this) {
      appContext.updateResources(locale)
    }
  }

  private fun Context.updateResources(locale: Locale) {
    Locale.setDefault(locale)

    val current = resources.configuration.locales.get(0)
    if (current == locale) return

    val config = Configuration(resources.configuration)

    locale.addToConfiguration(config)
    config.create(this)
  }

  private fun Locale.addToConfiguration(config: Configuration) {
    val defaultLocales = LocaleList.getDefault()
    val all = List<Locale>(defaultLocales.size()) { defaultLocales[it] }

    val locales = linkedSetOf(this)
    locales.addAll(all)

    config.setLocales(LocaleList(*locales.toTypedArray()))
  }

  private fun Configuration.create(context: Context): Context {
    return context.createConfigurationContext(this)
  }
}