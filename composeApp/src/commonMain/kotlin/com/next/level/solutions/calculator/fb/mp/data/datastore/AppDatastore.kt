package com.next.level.solutions.calculator.fb.mp.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.next.level.solutions.calculator.fb.mp.data.datastore.produce.path.ProducePath
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import okio.Path.Companion.toPath

class AppDatastore(
  producePath: ProducePath,
) {
  private val dataStore: DataStore<Preferences> by lazy {
    PreferenceDataStoreFactory.createWithPath(
      produceFile = { producePath.getPath("app_datastore.preferences_pb").toPath() },
    )
  }

  private val policyState: Preferences.Key<Boolean> = booleanPreferencesKey("policy")
  private val languageState: Preferences.Key<Boolean> = booleanPreferencesKey("language")
  private val tipToResetPassword: Preferences.Key<Boolean> = booleanPreferencesKey("tipToResetPassword")
  private val checkedOldFilesState: Preferences.Key<Boolean> = booleanPreferencesKey("checkedOldFiles")

  private val passwordState: Preferences.Key<String> = stringPreferencesKey("password")
  private val secureQuestionState: Preferences.Key<String> = stringPreferencesKey("secureQuestion")
  private val secureAnswerState: Preferences.Key<String> = stringPreferencesKey("secureAnswer")

  private val languageName: Preferences.Key<String> = stringPreferencesKey("languageName")

  suspend fun policyStateOnce(): Boolean = policyState.getOrDef(false).first()
  suspend fun policyState(value: Boolean): Unit = policyState.set(value)

  suspend fun languageStateOnce(): Boolean = languageState.getOrDef(false).first()
  suspend fun languageState(value: Boolean): Unit = languageState.set(value)

  suspend fun checkedOldFilesStateOnce(): Boolean = checkedOldFilesState.getOrDef(false).first()
  suspend fun checkedOldFilesState(value: Boolean): Unit = checkedOldFilesState.set(value)

  fun tipToResetPassword(): Flow<Boolean> = tipToResetPassword.getOrDef(true)
  suspend fun tipToResetPassword(value: Boolean): Unit = tipToResetPassword.set(value)

  fun languageName(): Flow<String?> = languageName.getOrDef("en")
  suspend fun languageName(value: String): Unit = languageName.set(value)

  suspend fun passwordStateOnce(): String = passwordState.getOrDef("").first()
  suspend fun passwordState(value: String): Unit = passwordState.set(value)

  suspend fun secureQuestionStateOnce(): String? = secureQuestionState.getOrNull().first()
  suspend fun secureQuestionState(value: String): Unit = secureQuestionState.set(value)

  suspend fun secureAnswerStateOnce(): String? = secureAnswerState.getOrNull().first()
  suspend fun secureAnswerState(value: String): Unit = secureAnswerState.set(value)

  private suspend fun <V> Preferences.Key<V>.set(value: V) {
    dataStore.edit { it[this] = value }
  }

  private fun <V> Preferences.Key<V>.getOrDef(default: V): Flow<V> {
    return dataStore.data.map { it[this] ?: default }
  }

  private fun <V> Preferences.Key<V>.getOrNull(): Flow<V?> {
    return dataStore.data.map { it[this] }
  }
}