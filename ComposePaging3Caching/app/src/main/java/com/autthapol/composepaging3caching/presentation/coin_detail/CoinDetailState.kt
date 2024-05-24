package com.autthapol.composepaging3caching.presentation.coin_detail

import com.autthapol.composepaging3caching.domain.model.Coin

data class CoinDetailState(
    val isLoading: Boolean = false,
    val coin: Coin? = null,
    val error: String = ""
)
