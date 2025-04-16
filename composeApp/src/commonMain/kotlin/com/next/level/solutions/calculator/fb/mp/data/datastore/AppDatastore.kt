package com.next.level.solutions.calculator.fb.mp.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.Preferences.Key
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.next.level.solutions.calculator.fb.mp.data.datastore.local.store.LocalStore
import com.next.level.solutions.calculator.fb.mp.data.datastore.produce.path.ProducePath
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized
import okio.Path.Companion.toPath

@OptIn(InternalCoroutinesApi::class)
class AppDatastore(
  producePath: ProducePath,
  val localStore: LocalStore,
) {
  private companion object {
    private var instance: DataStore<Preferences>? = null
  }

  private object Synchronized : SynchronizedObject()

  private val dataStore: DataStore<Preferences> by lazy {
    instance ?: synchronized(Synchronized) {
      PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath.getPath("app_datastore.preferences_pb").toPath() },
      ).also { instance = it }
    }
  }

  private val policyState: Key<Boolean> = booleanPreferencesKey("policy")
  private val languageState: Key<Boolean> = booleanPreferencesKey("language")
  private val tipToResetPassword: Key<Boolean> = booleanPreferencesKey("tipToResetPassword")
  private val checkedOldFilesState: Key<Boolean> = booleanPreferencesKey("checkedOldFiles")

  private val passwordState: Key<String> = stringPreferencesKey("password")
  private val secureQuestionState: Key<String> = stringPreferencesKey("secureQuestion")
  private val secureAnswerState: Key<String> = stringPreferencesKey("secureAnswer")
  private val languageName: Key<String> = stringPreferencesKey("languageName")

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

  private suspend fun <V> Key<V>.set(value: V) {
    dataStore.edit { it[this] = value }
  }

  private fun <V> Key<V>.getOrDef(default: V): Flow<V> {
    return dataStore.data.map { it[this] ?: default }
  }

  private fun <V> Key<V>.getOrNull(): Flow<V?> {
    return dataStore.data.map { it[this] }
  }
}