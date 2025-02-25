package com.next.level.solutions.calculator.fb.mp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.next.level.solutions.calculator.fb.mp.entity.db.TrashModelDB
import kotlinx.coroutines.flow.Flow

@Dao
interface TrashDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(vararg item: TrashModelDB)

  @Query("SELECT * FROM trash_db ORDER by date_added DESC")
  fun getByDateAdded(): Flow<List<TrashModelDB>>

  @Query("SELECT * FROM trash_db ORDER by size DESC")
  fun getByFileSize(): Flow<List<TrashModelDB>>

  @Query("SELECT * FROM trash_db ORDER by name ASC")
  fun getByName(): Flow<List<TrashModelDB>>

  @Query("DELETE FROM trash_db WHERE path = :path")
  suspend fun delete(path: String)
}