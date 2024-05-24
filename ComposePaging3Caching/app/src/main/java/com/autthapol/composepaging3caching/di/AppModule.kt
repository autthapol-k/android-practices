package com.autthapol.composepaging3caching.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.autthapol.composepaging3caching.data.local.CoinEntity
import com.autthapol.composepaging3caching.data.local.CryptoDatabase
import com.autthapol.composepaging3caching.data.remote.CoinRemoteMediator
import com.autthapol.composepaging3caching.data.remote.CryptoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCryptoDatabase(@ApplicationContext context: Context): CryptoDatabase {
        return Room.databaseBuilder(
            context,
            CryptoDatabase::class.java,
            "crypto_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCryptoApi(): CryptoApi {
        return Retrofit.Builder()
            .baseUrl(CryptoApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideCoinPager(
        cryptoDatabase: CryptoDatabase,
        cryptoApi: CryptoApi
    ): Pager<Int, CoinEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = CoinRemoteMediator(
                cryptoDatabase = cryptoDatabase,
                cryptoApi = cryptoApi
            ),
            pagingSourceFactory = {
                cryptoDatabase.coinDao.pagingSource()
            }
        )
    }
}