package com.autthapol.composepaging3caching.domain

data class Coin(
    val id: String,
    val name: String,
    val symbol: String,
    val rank: Int,
    val isNew: Boolean,
    val isActive: Boolean,
    val type: String,
)