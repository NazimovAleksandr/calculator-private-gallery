package com.next.level.solutions.calculator.fb.mp.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath
import org.koin.core.annotation.Single

@Single
class AppDatastore {
  companion object {
    private val dataStore by lazy { createDataStore() }

    private fun createDataStore(): DataStore<Preferences> = PreferenceDataStoreFactory
      .createWithPath(produceFile = { producePath("app_datastore.preferences_pb").toPath() })
  }

  private val policyState: Preferences.Key<Boolean> = booleanPreferencesKey("policyState")
  private val languageState: Preferences.Key<Boolean> = booleanPreferencesKey("languageState")

  fun policyState(): Flow<Boolean?> = policyState.get()
  suspend fun policyStateOnce(): Boolean = policyState().first() ?: false
  suspend fun policyState(value: Boolean): Unit = policyState.set(value)

  fun languageState(): Flow<Boolean?> = languageState.get()
  suspend fun languageStateOnce(): Boolean = languageState().first() ?: false
  suspend fun languageState(value: Boolean): Unit = languageState.set(value)

  private suspend fun <V> Preferences.Key<V>.set(value: V) {
    dataStore.edit { preferences ->
      preferences[this] = value
    }
  }

  private fun <V> Preferences.Key<V>.get(): Flow<V?> {
    return dataStore.data.map { preferences ->
      preferences[this]
    }
  }

}

expect fun producePath(name: String): String