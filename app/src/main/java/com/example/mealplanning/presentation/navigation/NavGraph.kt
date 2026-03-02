package com.example.mealplanning.presentation.navigation

import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mealplanning.presentation.screen.milestone.SearchMealPlanScreen
import com.example.mealplanning.presentation.screen.recipe.RecipeScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavScreen.SearchMealPlanScreen.route
    ) {
        composable(NavScreen.SearchMealPlanScreen.route) {
            SearchMealPlanScreen(
                onRecipeClick = {
                    Log.d("NavGraph","onRecipeClick")
                    navController.navigate(NavScreen.RecipeScreen.createRoute(it.id))
                }
            )
        }
        composable(NavScreen.RecipeScreen.route) {
            val recipeId = NavScreen.RecipeScreen.getRecipeId(it.arguments)
            Log.d("NavGraph","Я тут был")
            RecipeScreen(
                recipeId = recipeId
            )
        }
    }
}


sealed class NavScreen(val route: String) {
    data object SearchMealPlanScreen : NavScreen("search")
    data object RecipeScreen : NavScreen("recipe/{recipe_id}") {
        fun createRoute(recipeId: Int): String {
            return "recipe/$recipeId"
        }

        fun getRecipeId(arguments: Bundle?): Int {
            return arguments?.getString("recipe_id")?.toInt() ?: 0
        }
    }
}