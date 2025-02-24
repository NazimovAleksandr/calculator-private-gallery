package com.next.level.solutions.calculator.fb.mp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.next.level.solutions.calculator.fb.mp.entity.db.BrowserHistoryDB
import kotlinx.coroutines.flow.Flow

@Dao
interface BrowserHistoryDao {
    @Query("SELECT * FROM browser_history WHERE is_remove = 0 ORDER BY time DESC")
    fun getItems(): Flow<List<BrowserHistoryDB>>

    @Query("SELECT * FROM browser_history ORDER BY time DESC")
    fun getItemsWithRemoved(): Flow<List<BrowserHistoryDB>>

    @Query("SELECT * FROM browser_history WHERE url = :url AND date = :date LIMIT 1")
    suspend fun getItemByUrlAndDate(url: String, date: String): BrowserHistoryDB?

    @Query("SELECT * FROM browser_history WHERE time = :time LIMIT 1")
    suspend fun getItemByTime(time: Long): BrowserHistoryDB?

    @Query("SELECT * FROM browser_history WHERE url = :url LIMIT 1")
    suspend fun getItemByUrl(url: String): BrowserHistoryDB?

    @Query("SELECT * FROM browser_history WHERE is_ad = 1 ORDER BY time DESC LIMIT 1")
    suspend fun getLastItemByAd(): BrowserHistoryDB?

    @Query("SELECT * FROM browser_history WHERE id = :id LIMIT 1")
    suspend fun getItemById(id: Int): BrowserHistoryDB?

    @Query("SELECT * FROM browser_history WHERE id IN (:ids)")
    suspend fun getItemByIds(ids: List<Int>): List<BrowserHistoryDB>

    @Insert
    suspend fun insert(item: BrowserHistoryDB)

    @Update
    suspend fun update(item: BrowserHistoryDB)

    @Update
    suspend fun update(items: List<BrowserHistoryDB>)

    @Query("DELETE FROM browser_history WHERE id = :id")
    suspend fun deleteItemById(id: Int)

    @Query("DELETE FROM browser_history WHERE id IN (:ids)")
    suspend fun deleteItemsByIds(ids: List<Int>)

    @Query("DELETE FROM browser_history")
    suspend fun deleteAll()
}