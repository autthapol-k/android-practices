package com.autthapol.composepaging3caching.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CoinEntity::class], version = 1, exportSchema = false)
abstract class CryptoDatabase : RoomDatabase() {
    abstract val coinDao: CoinDao
}