package com.autthapol.composepaging3caching.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.autthapol.composepaging3caching.domain.Coin
import com.autthapol.composepaging3caching.ui.theme.ComposePaging3CachingTheme

@Composable
fun CoinItem(
    modifier: Modifier = Modifier, coin: Coin
) {
    Card(modifier = modifier, elevation = CardDefaults.cardElevation(4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f)
            ) {
                Text(
                    text = "${coin.name} (${coin.symbol})",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Rank: ${coin.rank}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Type: ${coin.type.uppercase()}",
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                )
            }
            Text(
                modifier = Modifier
                    .weight(1f),
                textAlign = TextAlign.End,
                fontWeight = FontWeight.SemiBold,

                color = if (coin.isActive) Color.Green else Color.Red,
                text = (if (coin.isActive) "Active" else "Inactive").uppercase()
            )
        }
    }
}

@Preview
@Composable
private fun CoinItemPreview() {
    ComposePaging3CachingTheme {
        CoinItem(
            coin = Coin(
                id = "btc-bitcoin",
                name = "Bitcoin",
                symbol = "BTC",
                rank = 1,
                isNew = false,
                isActive = true,
                type = "coin"
            )
        )
    }
}