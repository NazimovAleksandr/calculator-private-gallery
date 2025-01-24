package com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.LocaleList
import com.next.level.solutions.calculator.fb.mp.MainActivity
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.model.LanguageModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.util.Locale

actual fun getLanguageChanger(): LanguageChanger {
  return MainActivity.languageChanger?.value ?: throw IllegalStateException("LanguageChanger is not initialized")
}

actual fun getDefaultLocaleLanguageCode(): String {
  return Locale.getDefault().language ?: "en"
}

class LanguageChangerImpl(
  private val activity: Activity,
) : LanguageChanger {

  override fun updateLocale(
    languageModel: LanguageModel,
  ) {
    val locale = languageModel.toLocale()

    activity.updateLocale(locale)
    activity.recreate()
  }

  override fun selectedLocale(
    availableLanguages: MutableList<Pair<String, String>>,
  ): String {
    val languages = languages(availableLanguages)
    val selected = languages.flatten().firstOrNull { it?.first == Locale.getDefault().language }
    val selectedLocale = selected?.first ?: Locale.getDefault().language ?: "en"

    return selectedLocale
  }

  override fun languages(
    availableLanguages: MutableList<Pair<String, String>>,
  ): ImmutableList<List<Pair<String, String>?>> {
    return availableLanguages
      .let { list ->
        val currentLocal = list.firstOrNull { pair ->
          pair.first == Locale.getDefault().language
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

  private fun LanguageModel.toLocale(): Locale {
    return Locale(code, country, variant)
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