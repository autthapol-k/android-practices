package com.autthapol.persistentscrollpositioncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.autthapol.persistentscrollpositioncompose.ui.theme.PersistentScrollPositionComposeTheme
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
class MainActivity : ComponentActivity() {

    private val prefs by lazy {
        application.getSharedPreferences("prefs", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val scrollPosition = prefs.getInt("scroll_position", 0)

        setContent {
            PersistentScrollPositionComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        val lazyListState = rememberLazyListState(
                            initialFirstVisibleItemIndex = scrollPosition
                        )

                        LaunchedEffect(lazyListState) {
                            snapshotFlow {
                                lazyListState.firstVisibleItemIndex
                            }.debounce(500L)
                                .collectLatest { index ->
                                    prefs.edit()
                                        .putInt("scroll_position", index)
                                        .apply()
                                }
                        }

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            state = lazyListState
                        ) {
                            items(100) {
                                Text(modifier = Modifier.padding(16.dp), text = "Item $it")
                            }
                        }
                    }
                }
            }
        }
    }
}