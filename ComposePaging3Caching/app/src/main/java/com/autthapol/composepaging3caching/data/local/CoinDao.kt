package com.autthapol.composepaging3caching.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CoinDao {
    @Upsert
    suspend fun upsertAll(coins: List<CoinEntity>)

    @Query("SELECT * FROM coin_entity")
    fun pagingSource(): PagingSource<Int, CoinEntity>

    @Query("DELETE FROM coin_entity")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM coin_entity")
    suspend fun getCount(): Int
}