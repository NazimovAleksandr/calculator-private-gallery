package com.next.level.solutions.calculator.fb.mp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.next.level.solutions.calculator.fb.mp.entity.db.FileDataDB
import com.next.level.solutions.calculator.fb.mp.entity.db.NoteModelDB
import kotlinx.coroutines.flow.Flow

@Suppress("AndroidUnresolvedRoomSqlReference")
@Dao
interface NoteDao : DaoDB {
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

  @Delete
  suspend fun delete(vararg item: NoteModelDB)

  override suspend fun insert(vararg item: FileDataDB) {
    insert(*item.mapNotNull { it as? NoteModelDB }.toTypedArray())
  }

  override suspend fun delete(vararg item: FileDataDB) {
    delete(*item.mapNotNull { it as? NoteModelDB }.toTypedArray())
  }

  override fun byDate(): Flow<List<FileDataDB>> {
    return getByDateAdded()
  }

  override fun bySize(): Flow<List<FileDataDB>> {
    return getByFileSize()
  }

  override fun byName(): Flow<List<FileDataDB>> {
    return getByName()
  }
}