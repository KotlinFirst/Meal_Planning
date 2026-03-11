package com.example.mealplanning.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.mealplanning.data.remote.MealPlanApiService
import com.example.mealplanning.presentation.navigation.NavGraph
import com.example.mealplanning.presentation.screen.instructions.InstructionsScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mealPlanApi: MealPlanApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _root_ide_package_.com.example.mealplanning.presentation.ui.theme.MealPlanningTheme {
//                NavGraph()
                InstructionsScreen(recipeId = 324694)
            }
        }
    }
}

