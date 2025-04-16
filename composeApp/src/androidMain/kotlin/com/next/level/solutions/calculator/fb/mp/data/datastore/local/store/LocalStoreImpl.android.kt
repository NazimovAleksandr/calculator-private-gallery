package com.next.level.solutions.calculator.fb.mp.data.datastore.local.store

import android.content.SharedPreferences
import androidx.compose.runtime.Immutable
import com.next.level.solutions.calculator.fb.mp.utils.delegate

@Immutable
class LocalStoreImpl(
  sharedPreferences: SharedPreferences
) : LocalStore {
  override var darkTheme: Boolean by sharedPreferences.delegate(true)
}