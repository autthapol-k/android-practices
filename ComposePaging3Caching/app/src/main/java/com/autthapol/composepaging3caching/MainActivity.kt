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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.autthapol.composepaging3caching.core.common.Constants
import com.autthapol.composepaging3caching.presentation.coin_detail.CoinDetailScreen
import com.autthapol.composepaging3caching.presentation.coin_detail.CoinDetailViewModel
import com.autthapol.composepaging3caching.presentation.coin_list.CoinListScreen
import com.autthapol.composepaging3caching.presentation.coin_list.CoinListViewModel
import com.autthapol.composepaging3caching.ui.theme.ComposePaging3CachingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposePaging3CachingTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = "coin_list_screen"
                        ) {
                            composable("coin_list_screen") {
                                val viewModel = hiltViewModel<CoinListViewModel>()
                                val coins = viewModel.coinPagingFlow.collectAsLazyPagingItems()
                                CoinListScreen(
                                    coins = coins,
                                    onItemClicked = {
                                        navController.navigate("coin_detail_screen/${it.id}")
                                    }
                                )
                            }
                            composable("coin_detail_screen/{${Constants.PARAM_COIN_ID}}") {
                                val viewModel = hiltViewModel<CoinDetailViewModel>()
                                val state = viewModel.state.value
                                CoinDetailScreen(
                                    state = state
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}