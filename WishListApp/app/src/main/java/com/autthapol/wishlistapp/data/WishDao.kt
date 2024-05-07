package com.autthapol.wishlistapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WishDao {
    @Query("SELECT * FROM `wish-table`")
    fun getAll(): Flow<List<Wish>>

    @Query("SELECT * FROM `wish-table` WHERE id = :id")
    fun getById(id: Long): Flow<Wish>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(entity: Wish)

    @Update
    suspend fun update(entity: Wish)

    @Delete
    suspend fun delete(entity: Wish)
}