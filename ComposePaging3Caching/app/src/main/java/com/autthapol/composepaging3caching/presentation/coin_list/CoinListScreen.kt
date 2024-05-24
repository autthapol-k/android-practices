package com.autthapol.composepaging3caching.presentation.coin_list

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.autthapol.composepaging3caching.domain.model.Coin

@Composable
fun CoinListScreen(
    modifier: Modifier = Modifier,
    coins: LazyPagingItems<Coin>,
    onItemClicked: (Coin) -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = coins.loadState) {
        if (coins.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: " + (coins.loadState.refresh as LoadState.Error).error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (coins.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(coins.itemCount, key = coins.itemKey {
                    it.id
                }) { index ->
                    coins[index]?.let {
                        CoinItem(coin = it, onItemClicked = onItemClicked)
                    }
                }
                item {
                    if (coins.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}