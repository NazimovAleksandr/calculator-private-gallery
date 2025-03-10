package com.next.level.solutions.calculator.fb.mp.data.database.dao

import com.next.level.solutions.calculator.fb.mp.entity.db.FileDataDB
import kotlinx.coroutines.flow.Flow

interface DaoDB {
  suspend fun insert(vararg item: FileDataDB)
  suspend fun delete(vararg item: FileDataDB)
  fun byDate(): Flow<List<FileDataDB>>
  fun bySize(): Flow<List<FileDataDB>>
  fun byName(): Flow<List<FileDataDB>>
}