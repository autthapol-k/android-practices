package com.autthapol.composepaging3caching.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coin_entity")
data class CoinEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val isNew: Boolean,
    val isActive: Boolean,
    val type: String,
)
