package com.autthapol.onetimeeventantipatternapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.autthapol.onetimeeventantipatternapp.ui.theme.OneTimeEventAntiPatternAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OneTimeEventAntiPatternAppTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        val viewModel = viewModel<MainViewModel>()
                        val state = viewModel.state

                        ObserveAsEvents(flow = viewModel.navigationEventsChannelFlow) { event ->
                            when (event) {
                                is NavigationEvent.NavigateToProfile -> {
                                    navController.navigate("profile")
                                }

                                is NavigationEvent.CountEvent -> {
                                    println("COUNT: ${event.count}")
                                }
                            }
                        }

//                        LaunchedEffect(key1 = state.isLoggedIn) {
//                            if (state.isLoggedIn) {
//                                navController.navigate("profile")
//                                state.onConsumed()
//                            }
//                        }

                        LoginScreen(state = state, onLongClicked = viewModel::login)
                    }
                    composable("profile") {
                        ProfileScreen()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("COUNT INTERRUPTED")
    }
}

@Composable
fun <T> ObserveAsEvents(flow: Flow<T>, onEvent: (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(key1 = lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//            Dispatchers.Main.immediate process before view destruction can happen
            withContext(Dispatchers.Main.immediate) {
                flow.collect(onEvent)
            }
        }
    }
}

@Composable
fun LoginScreen(
    state: LoginState,
    onLongClicked: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onLongClicked) {
                Text(text = "Login")
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Profile")
    }
}