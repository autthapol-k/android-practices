package com.autthapol.composepaging3caching.core.mappers

import com.autthapol.composepaging3caching.data.local.entity.CoinEntity
import com.autthapol.composepaging3caching.data.remote.dto.CoinDto
import com.autthapol.composepaging3caching.domain.model.Coin

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

fun CoinDto.toCoin() = Coin(
    id = id,
    name = name,
    symbol = symbol,
    rank = rank,
    isActive = isActive,
    isNew = isNew,
    type = type,
    description = description,
    logo = logo
)