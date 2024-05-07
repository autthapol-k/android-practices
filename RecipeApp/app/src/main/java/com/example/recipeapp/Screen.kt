package com.example.recipeapp

sealed  class Screen(val route: String) {
    data object RecipeScreen: Screen("recipescreen")
    data object RecipeDetailScreen: Screen("recipedetailscreen")
}