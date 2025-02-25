package com.next.level.solutions.calculator.fb.mp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.next.level.solutions.calculator.fb.mp.entity.db.NoteModelDB
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(vararg item: NoteModelDB)

  @Query("SELECT * FROM notes_db ORDER by date_added DESC")
  fun getByDateAdded(): Flow<List<NoteModelDB>>

  @Query("SELECT * FROM notes_db ORDER by date_added DESC")
  fun getByFileSize(): Flow<List<NoteModelDB>>

  @Query("SELECT * FROM notes_db ORDER by name ASC")
  fun getByName(): Flow<List<NoteModelDB>>

  @Query("SELECT * FROM notes_db WHERE date_added = :dateAdded")
  suspend fun get(dateAdded: String): NoteModelDB

  @Query("DELETE FROM notes_db WHERE date_added = :dateAdded")
  suspend fun delete(dateAdded: String)
}