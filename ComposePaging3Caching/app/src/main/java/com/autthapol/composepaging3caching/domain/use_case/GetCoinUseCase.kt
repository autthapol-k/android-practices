package com.autthapol.composepaging3caching.domain.use_case

import com.autthapol.composepaging3caching.domain.model.Coin
import com.autthapol.composepaging3caching.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import com.autthapol.composepaging3caching.core.common.Result
import kotlinx.coroutines.flow.Flow

class GetCoinUseCase @Inject constructor(
    private val cryptoRepository: CryptoRepository
) {
    operator fun invoke(id: String): Flow<Result<Coin>> = flow {
        try {
            emit(Result.Loading())
            val coin = cryptoRepository.getCoin(id)
            emit(Result.Success(coin))
        } catch (e: HttpException) {
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch (e: IOException) {
            emit(Result.Error("Couldn't reach server. Check your internet connection."))
        }
    }
}