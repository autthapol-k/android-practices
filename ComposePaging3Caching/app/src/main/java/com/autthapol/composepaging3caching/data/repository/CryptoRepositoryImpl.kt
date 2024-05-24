package com.autthapol.composepaging3caching.data.repository

import com.autthapol.composepaging3caching.core.mappers.toCoin
import com.autthapol.composepaging3caching.data.remote.CryptoApi
import com.autthapol.composepaging3caching.domain.model.Coin
import com.autthapol.composepaging3caching.domain.repository.CryptoRepository
import javax.inject.Inject

class CryptoRepositoryImpl @Inject constructor(
    private val cryptoApi: CryptoApi
) : CryptoRepository {
    override suspend fun getCoin(id: String): Coin {
        return cryptoApi.getCoin(id).toCoin()
    }
}