package com.example.mealplanning.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mealplanning.data.local.database.MealPlanDao
import com.example.mealplanning.data.remote.MealPlanApiService
import com.example.mealplanning.presentation.navigation.NavGraph
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mealPlanApi: MealPlanApiService
    @Inject
    lateinit var dao: MealPlanDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _root_ide_package_.com.example.mealplanning.presentation.ui.theme.MealPlanningTheme {
//                RecipeScreen(recipeId = 324694, onButtonClick = {})
//                Test()
                NavGraph()
//                FavoriteRecipesScreen()
//                InstructionsScreen(recipeId = 324694)
            }
        }
    }
}

