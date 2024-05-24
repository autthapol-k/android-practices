package com.autthapol.composepaging3caching.presentation.coin_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.autthapol.composepaging3caching.core.common.Constants
import com.autthapol.composepaging3caching.core.common.Result
import com.autthapol.composepaging3caching.domain.use_case.GetCoinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(CoinDetailState())
    val state: State<CoinDetailState> = _state

    init {
        savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.also { id ->
            getCoin(id)
        }
    }

    private fun getCoin(id: String) {
        getCoinUseCase(id).onEach { result ->
            when (result) {
                is Result.Loading -> _state.value = CoinDetailState(isLoading = true)
                is Result.Success -> _state.value = CoinDetailState(coin = result.data)
                is Result.Error -> _state.value =
                    CoinDetailState(error = result.message ?: "An unexpected error occurred")
            }
        }.launchIn(viewModelScope)
    }
}