package com.next.level.solutions.calculator.fb.mp.ui.screen.language.changer

import android.content.SharedPreferences
import androidx.compose.runtime.Immutable
import org.json.JSONObject
import java.util.Locale
import androidx.core.content.edit

@Immutable
class ChangerLocalStore(
  private val sharedPreferences: SharedPreferences
) {
  fun getLocale(): Locale {
    val locale = sharedPreferences.getString(LOCALE, null)

    return when (locale.isNullOrBlank()) {
      true -> Locale.getDefault()

      false -> {
        val json = JSONObject(locale)
        val language = json.getString(LANGUAGE)
        val country = json.getString(COUNTRY)
        val variant = json.getString(VARIANT)

        Locale(language, country, variant)
      }
    }
  }

  fun persistLocale(locale: Locale) {
    val json = JSONObject().apply {
      put(LANGUAGE, locale.language)
      put(COUNTRY, locale.country)
      put(VARIANT, locale.variant)
    }

    sharedPreferences.edit { putString(LOCALE, json.toString()) }
  }

  companion object {
    private const val LOCALE = "LOCALE"
    private const val LANGUAGE = "LANGUAGE"
    private const val COUNTRY = "COUNTRY"
    private const val VARIANT = "VARIANT"
  }
}