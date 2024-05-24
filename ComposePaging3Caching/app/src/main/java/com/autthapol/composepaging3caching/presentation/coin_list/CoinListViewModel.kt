package com.autthapol.composepaging3caching.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.autthapol.composepaging3caching.core.mappers.toCoin
import com.autthapol.composepaging3caching.data.local.entity.CoinEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    pager: Pager<Int, CoinEntity>
) : ViewModel() {

    val coinPagingFlow = pager.flow
        .map { pagingData ->
            pagingData.map {
                it.toCoin()
            }
        }
        .cachedIn(viewModelScope)
}