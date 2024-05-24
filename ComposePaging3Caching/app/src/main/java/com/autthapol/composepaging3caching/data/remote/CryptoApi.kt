package com.autthapol.composepaging3caching.data.remote

import com.autthapol.composepaging3caching.data.remote.dto.CoinDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CryptoApi {

    @GET("coins")
    suspend fun getCoins(
        @Query("page") page: Int,
        @Query("per_page") pageCount: Int
    ): List<CoinDto>

    @GET("coins/{id}")
    suspend fun getCoin(
        @Path("id") id: String
    ): CoinDto

    companion object {
        const val BASE_URL = "https://api.coinpaprika.com/v1/"
    }
}