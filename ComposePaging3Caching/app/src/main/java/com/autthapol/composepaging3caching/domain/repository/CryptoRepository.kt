package com.autthapol.composepaging3caching.domain.repository

import com.autthapol.composepaging3caching.domain.model.Coin

interface CryptoRepository {
    suspend fun getCoin(id: String): Coin
}