package com.autthapol.composepaging3caching.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.autthapol.composepaging3caching.core.mappers.toEntity
import com.autthapol.composepaging3caching.data.local.entity.CoinEntity
import com.autthapol.composepaging3caching.data.local.CryptoDatabase
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CoinRemoteMediator(
    private val cryptoDatabase: CryptoDatabase,
    private val cryptoApi: CryptoApi
) : RemoteMediator<Int, CoinEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CoinEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        val count = cryptoDatabase.coinDao.getCount()
                        (count / state.config.pageSize) + 1
                    }
                }
            }

            val coins = cryptoApi.getCoins(
                page = loadKey,
                pageCount = state.config.pageSize
            )

            cryptoDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    cryptoDatabase.coinDao.clearAll()
                }
                val coinEntities = coins.map { it.toEntity() }
                    .slice(
                        IntRange(
                            start = ((loadKey - 1) * state.config.pageSize),
                            endInclusive = (loadKey * state.config.pageSize) - 1
                        )
                    )
                cryptoDatabase.coinDao.upsertAll(coinEntities)
            }

            MediatorResult.Success(endOfPaginationReached = coins.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}