package com.next.level.solutions.calculator.fb.mp.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.next.level.solutions.calculator.fb.mp.ui.screen.language.model.LanguageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath

class AppDatastore {
  companion object {
    private val dataStore by lazy { createDataStore() }

    private fun createDataStore(): DataStore<Preferences> = PreferenceDataStoreFactory
      .createWithPath(produceFile = { producePath("app_datastore.preferences_pb").toPath() })
  }

  private val policyState: Preferences.Key<Boolean> = booleanPreferencesKey("policy")
  private val languageState: Preferences.Key<Boolean> = booleanPreferencesKey("language")
  private val tipToResetPassword: Preferences.Key<Boolean> = booleanPreferencesKey("language")

  private val passwordState: Preferences.Key<String> = stringPreferencesKey("password")
  private val secureQuestionState: Preferences.Key<String> = stringPreferencesKey("secureQuestion")
  private val secureAnswerState: Preferences.Key<String> = stringPreferencesKey("secureAnswer")

  private val languageName: Preferences.Key<String> = stringPreferencesKey("languageName")
  private val languageModel: Preferences.Key<String> = stringPreferencesKey("languageModel")

  suspend fun policyStateOnce(): Boolean = policyState.get().first() ?: false
  suspend fun policyState(value: Boolean): Unit = policyState.set(value)

  suspend fun languageStateOnce(): Boolean = languageState.get().first() ?: false
  suspend fun languageState(value: Boolean): Unit = languageState.set(value)

  fun tipToResetPassword(): Flow<Boolean?> = tipToResetPassword.get()
  suspend fun tipToResetPassword(value: Boolean): Unit = tipToResetPassword.set(value)

  fun languageName(): Flow<String?> = languageName.get()
  suspend fun languageNameOnce(): String = languageName().first() ?: ""
  suspend fun languageName(value: String): Unit = languageName.set(value)

  suspend fun languageModelOnce(): LanguageModel? = languageModel.get().map { it?.toLanguageModel() }.first()
  suspend fun languageModel(value: LanguageModel): Unit = languageModel.set(value.mapToString())
  private fun String.toLanguageModel(): LanguageModel? = Json.decodeFromString<LanguageModel?>(this)
  private fun LanguageModel.mapToString(): String = Json.encodeToString(this)

  suspend fun passwordStateOnce(): String = passwordState.get().first() ?: ""
  suspend fun passwordState(value: String): Unit = passwordState.set(value)

  suspend fun secureQuestionStateOnce(): String? = secureQuestionState.get().first()
  suspend fun secureQuestionState(value: String): Unit = secureQuestionState.set(value)

  suspend fun secureAnswerStateOnce(): String? = secureAnswerState.get().first()
  suspend fun secureAnswerState(value: String): Unit = secureAnswerState.set(value)

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