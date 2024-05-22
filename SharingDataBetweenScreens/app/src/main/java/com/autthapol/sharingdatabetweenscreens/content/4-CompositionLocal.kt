package com.autthapol.sharingdatabetweenscreens.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.autthapol.sharingdatabetweenscreens.ui.theme.SharingDataBetweenScreensTheme
import kotlinx.coroutines.launch

val LocalSnackbarHostState = compositionLocalOf {
    SnackbarHostState()
}

@Composable
fun AppRoot() {
    val snackbarHostState = LocalSnackbarHostState.current
    CompositionLocalProvider(value = LocalSnackbarHostState provides snackbarHostState) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = LocalSnackbarHostState.current)
            }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                MyScreen()
            }
        }
    }
}

@Composable
private fun MyScreen() {
    val snackHostState = LocalSnackbarHostState.current
    val scope = rememberCoroutineScope()
    Button(modifier = Modifier.wrapContentSize(), onClick = {
        scope.launch {
            snackHostState.showSnackbar("Hello World!")
        }
    }) {
        Text(text = "Show snackbar")
    }
}

@Preview
@Composable
private fun MyScreenPreview() {
    SharingDataBetweenScreensTheme {
        MyScreen()
    }
}