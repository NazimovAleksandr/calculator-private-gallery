package com.next.level.solutions.calculator.fb.mp.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <reified T : Any> SharedPreferences.delegate(
  defValue: T,
  newValue: T? = null
): ReadWriteProperty<Any?, T> {
  return SharedPreferencesDelegate(this, defValue, newValue)
}

class SharedPreferencesDelegate<T : Any>(
  private val pref: SharedPreferences,
  private val defValue: T,
  private val newValue: T?,
) : ReadWriteProperty<Any?, T> {

  override fun getValue(thisRef: Any?, property: KProperty<*>): T {
    return pref.get(property.name, defValue).also { pref.set(property.name, newValue) }
  }

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    pref.set(property.name, value)
  }

  private fun <T : Any> SharedPreferences.get(key: String, defValue: T): T {
    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    return when (defValue) {
      is String -> getString(key, defValue) ?: defValue
      is Boolean -> getBoolean(key, defValue)
      is Float -> getFloat(key, defValue)
      is Long -> getLong(key, defValue)
      is Int -> getInt(key, defValue)

      else -> defValue
    } as T
  }

  private fun <T : Any> SharedPreferences.set(key: String, value: T?) {
    value ?: return

    when (value) {
      is Boolean -> edit { putBoolean(key, value) }
      is String -> edit { putString(key, value) }
      is Float -> edit { putFloat(key, value) }
      is Long -> edit { putLong(key, value) }
      is Int -> edit { putInt(key, value) }
    }
  }
}