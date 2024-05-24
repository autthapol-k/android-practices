package com.autthapol.composepaging3caching

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.autthapol.composepaging3caching.presentation.CoinListScreen
import com.autthapol.composepaging3caching.presentation.CoinListViewModel
import com.autthapol.composepaging3caching.ui.theme.ComposePaging3CachingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposePaging3CachingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                    }
                    val viewModel = hiltViewModel<CoinListViewModel>()
                    val coins = viewModel.coinPagingFlow.collectAsLazyPagingItems()
                    CoinListScreen(modifier = Modifier.padding(innerPadding), coins = coins)
                }
            }
        }
    }
}