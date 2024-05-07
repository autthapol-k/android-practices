package com.example.recipeapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun RecipeApp(navHostController: NavHostController) {
    val recipeViewModel: MainViewModel = viewModel()
    val viewState by recipeViewModel.categoriesState

    NavHost(navController = navHostController, startDestination = Screen.RecipeScreen.route) {
        composable(route = Screen.RecipeScreen.route) {
            RecipeScreen(viewState = viewState) {
                navHostController.currentBackStackEntry?.savedStateHandle?.set("category", it)
                navHostController.navigate(Screen.RecipeDetailScreen.route)
            }
        }
        composable(route = Screen.RecipeDetailScreen.route) {
            val category =
                navHostController.previousBackStackEntry?.savedStateHandle?.get<Category>("category")
            category?.let {
                CategoryDetailScreen(category = category)
            }
        }
    }
}