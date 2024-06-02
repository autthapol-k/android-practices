package com.autthapol.derivedstateofcasestudy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.autthapol.derivedstateofcasestudy.ui.theme.DerivedStateOfCaseStudyTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DerivedStateOfCaseStudyTheme {

                val lazyListState = rememberLazyListState()
                var isEnabledButton by remember {
                    mutableStateOf(true)
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        ScrollToTopButton(
                            lazyListState = lazyListState,
                            isEnabledState = isEnabledButton
                        )
                    }
                ) { innerPadding ->
                    LazyColumn(
                        state = lazyListState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(100) {
                            Text(text = "Item: $it",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                                    .clickable {
                                        isEnabledButton = !isEnabledButton
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScrollToTopButton(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    isEnabledState: Boolean
) {

    val scope = rememberCoroutineScope()

    val showScrollToTopButton by remember(isEnabledState) {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex >= 5 && isEnabledState
        }
    }

//    val showScrollToTopButton = remember(lazyListState.firstVisibleItemIndex) {
//        lazyListState.firstVisibleItemIndex >= 5
//    }

    if (showScrollToTopButton) {
        FloatingActionButton(onClick = {
            scope.launch {
                lazyListState.animateScrollToItem(0)
            }
        }) {
            Icon(imageVector = Icons.Default.KeyboardArrowUp, contentDescription = null)
        }
    }

}
