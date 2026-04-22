package com.example.mealplanning.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Addchart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.QueuePlayNext
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavPanel(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String,
) {
    FAVORITE("favorite","Понравившиеся", Icons.Default.Favorite,"Favorite"),
    MEAL_PLAN("mealPlan","Планирование",Icons.Default.QueuePlayNext,"Meal Plan"),
    CHART("chart","Графики", Icons.Default.Addchart,"chart")
}