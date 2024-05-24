package com.autthapol.composepaging3caching.core.mappers

import com.autthapol.composepaging3caching.data.local.CoinEntity
import com.autthapol.composepaging3caching.data.remote.CoinDto
import com.autthapol.composepaging3caching.domain.Coin

fun CoinDto.toEntity(): CoinEntity = CoinEntity(
    id = id,
    name = name,
    symbol = symbol,
    rank = rank,
    isActive = isActive,
    isNew = isNew,
    type = type
)

fun CoinEntity.toCoin() = Coin(
    id = id,
    name = name,
    symbol = symbol,
    rank = rank,
    isActive = isActive,
    isNew = isNew,
    type = type
)