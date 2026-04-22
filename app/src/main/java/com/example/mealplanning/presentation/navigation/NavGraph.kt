package com.example.mealplanning.presentation.navigation

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mealplanning.presentation.navigation.composable.NavBottomBar
import com.example.mealplanning.presentation.navigation.composable.currentRoute
import com.example.mealplanning.presentation.screen.favoriteFood.FavoriteRecipesScreen
import com.example.mealplanning.presentation.screen.instructions.InstructionsScreen
import com.example.mealplanning.presentation.screen.milestone.SearchMealPlanScreen
import com.example.mealplanning.presentation.screen.recipe.RecipeScreen
import java.net.IDN

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar ={
            val currentRoute = currentRoute(navController)

            if (currentRoute in BottomNavPanel.entries.map {
                it.route
                }){
                NavBottomBar(
                    navController = navController,
                    onFavoriteClick = {
                        navController.navigate(BottomNavPanel.FAVORITE.route)
                    },
                    onChartClick = {},
                    onMealPlanClick = {}
                )

            }
        }
    ) {paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = NavScreen.SearchMealPlanScreen.route
        ) {
            composable(NavScreen.SearchMealPlanScreen.route) {
                SearchMealPlanScreen(
                    onRecipeClick = {
                        navController.navigate(NavScreen.RecipeScreen.createRoute(it.id))
                    }
                )
            }
            composable(NavScreen.RecipeScreen.route) {
                val recipeId = NavScreen.RecipeScreen.getRecipeId(it.arguments)
                RecipeScreen(
                    recipeId = recipeId,
                    onButtonClick = { recipeId ->
                        navController.navigate(NavScreen.InstructionScreen.createRoute(recipeId))
                    }
                )
            }
            composable(NavScreen.InstructionScreen.route) {
                val recipeId = NavScreen.InstructionScreen.getRecipeId(it.arguments)
                InstructionsScreen(
                    recipeId = recipeId
                )
            }
            composable(NavScreen.FavoriteScreen.route) {
                FavoriteRecipesScreen(
                    onRecipeClick = {
                        navController.navigate(NavScreen.RecipeScreen.createRoute(it.recipeId))
                    }
                )
            }
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

    data object InstructionScreen : NavScreen("instructions/{recipe_id}") {
        fun createRoute(recipeId: Int): String {
            return "instructions/$recipeId"
        }

        fun getRecipeId(arguments: Bundle?): Int {
            return arguments?.getString("recipe_id")?.toInt() ?: 0
        }
    }

    data object FavoriteScreen : NavScreen("favorite")
    data object ChartScreen : NavScreen("chart")
}