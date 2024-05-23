package com.autthapol.pulltorefreshcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.autthapol.pulltorefreshcompose.ui.theme.PullToRefreshComposeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PullToRefreshComposeTheme {
                Scaffold { paddingValues ->
                    Surface(
                        modifier = Modifier.fillMaxSize()
                            .padding(paddingValues),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val items = remember {
                            (0..100).map {
                                "Item $it"
                            }
                        }
                        var isRefreshing by remember {
                            mutableStateOf(false)
                        }
                        val scope = rememberCoroutineScope()
                        Box(modifier = Modifier.fillMaxSize()) {
                            PullToRefreshLazyColumn(
                                items = items,
                                content = { title ->
                                    Text(text = title, modifier = Modifier.padding(16.dp))
                                },
                                isRefreshing = isRefreshing,
                                onRefresh = {
                                    scope.launch {
                                        isRefreshing = true
                                        delay(3000L) // Simulate API call
                                        isRefreshing = false
                                    }
                                }
                            )
                            Button(
                                modifier = Modifier.align(Alignment.BottomCenter),
                                onClick = { isRefreshing = true }
                            ) {
                                Text(text = "Refresh")
                            }
                        }
                    }

                }
            }
        }
    }
}