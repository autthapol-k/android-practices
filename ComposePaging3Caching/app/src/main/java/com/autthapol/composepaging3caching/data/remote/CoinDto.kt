package com.autthapol.composepaging3caching.data.remote

import com.squareup.moshi.Json

data class CoinDto(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    @field:Json(name = "is_new")
    val isNew: Boolean,
    @field:Json(name = "is_active")
    val isActive: Boolean,
    val type: String,
)