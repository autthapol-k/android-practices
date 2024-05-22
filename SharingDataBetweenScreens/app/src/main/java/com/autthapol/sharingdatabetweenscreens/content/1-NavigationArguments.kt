package com.autthapol.sharingdatabetweenscreens.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun NavigationArgsSample() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "screen1") {
        composable("screen1") {
            Screen1(onNavigateToScreen1 = {
                navController.navigate("screen2/$it")
            })
        }
        composable("screen2/{my_param}",
            arguments = listOf(
                navArgument("my_param") {
                    type = NavType.StringType
                }
            )
        ) {
            val param = it.arguments?.getString("my_param") ?: ""
            Screen2(param = param)
        }
    }
}

@Composable
private fun Screen1(onNavigateToScreen1: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = {
            onNavigateToScreen1("Hello World!")
        }) {
            Text(text = "Click me")
        }
    }
}

@Composable
private fun Screen2(param: String) {
    Text(text = param)
}