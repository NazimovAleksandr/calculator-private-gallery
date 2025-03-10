package com.next.level.solutions.calculator.fb.mp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.next.level.solutions.calculator.fb.mp.entity.db.BrowserHistoryDB
import com.next.level.solutions.calculator.fb.mp.entity.db.FileModelDB
import kotlinx.coroutines.flow.Flow

@Dao
interface BrowserHistoryDao {
    @Query("SELECT * FROM browser_history ORDER BY time DESC")
    fun getItems(): Flow<List<BrowserHistoryDB>>

    @Query("SELECT * FROM browser_history WHERE url = :url LIMIT 1")
    suspend fun getItemByUrl(url: String): BrowserHistoryDB?

    @Insert
    suspend fun insert(item: BrowserHistoryDB)

    @Update
    suspend fun update(item: BrowserHistoryDB)

    @Delete
    suspend fun delete(vararg item: BrowserHistoryDB)
}