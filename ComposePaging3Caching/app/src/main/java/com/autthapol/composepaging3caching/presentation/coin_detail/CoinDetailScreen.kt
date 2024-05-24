package com.autthapol.composepaging3caching.presentation.coin_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.autthapol.composepaging3caching.domain.model.Coin
import com.autthapol.composepaging3caching.ui.theme.ComposePaging3CachingTheme

@Composable
fun CoinDetailScreen(
    modifier: Modifier = Modifier, state: CoinDetailState
) {
    Box(modifier = modifier.fillMaxSize()) {
        state.coin?.let { coin ->
            LazyColumn(
                modifier = modifier.fillMaxSize(), contentPadding = PaddingValues(24.dp)
            ) {
                item {
                    Column(modifier = Modifier.fillMaxSize()) {
                        AsyncImage(
                            modifier = Modifier.scale(1f),
                            model = coin.logo,
                            contentDescription = "logo"
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = coin.name + " (" + coin.symbol + ")",
                            style = MaterialTheme.typography.headlineMedium,
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = coin.description ?: "")
                    }
                }
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CoinDetailScreenPreview() {
    ComposePaging3CachingTheme {
        CoinDetailScreen(
            state = CoinDetailState(
                coin = Coin(
                    id = "btc-bitcoin",
                    name = "Bitcoin",
                    symbol = "BTC",
                    rank = 1,
                    isNew = false,
                    isActive = true,
                    type = "coin",
                    description = "Bitcoin is a cryptocurrency and worldwide payment system. It is the first decentralized digital currency, as the system works without a central bank or single administrator.",
                    logo = "https://static.coinpaprika.com/coin/btc-bitcoin/logo.png"
                )
            )
        )
    }
}
