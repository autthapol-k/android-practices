package com.autthapol.sharedelementtransitioncomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.autthapol.sharedelementtransitioncomposeapp.ui.theme.SharedElementTransitionComposeAppTheme

@OptIn(ExperimentalSharedTransitionApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SharedElementTransitionComposeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    SharedTransitionLayout {
                        val navController = rememberNavController()
                        NavHost(navController = navController, startDestination = "list") {
                            composable("list") {
                                ListScreen(animateVisibilityScope = this,
                                    onItemClicked = { resId, text ->
                                        navController.navigate("detail/$resId/$text")
                                    })
                            }
                            composable(
                                "detail/{resId}/{text}", arguments = listOf(
                                    navArgument("resId") {
                                        type = NavType.IntType
                                    },
                                    navArgument("text") {
                                        type = NavType.StringType
                                    },
                                )
                            ) {
                                val resId = it.arguments?.getInt("resId") ?: 0
                                val text = it.arguments?.getString("text") ?: ""
                                DetailScreen(
                                    animateVisibilityScope = this, resId = resId, text = text
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}