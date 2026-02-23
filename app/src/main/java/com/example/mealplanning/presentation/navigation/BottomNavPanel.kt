package com.example.mealplanning.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.QueuePlayNext
import androidx.compose.material.icons.filled.Today
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavPanel(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String,
) {
    TODAY("today","Сегодня", Icons.Default.Today,"Today"),
    MEAL_PLAN("mealPlan","Планирование",Icons.Default.QueuePlayNext,"Meal Plan"),
    CALENDAR("calendar","Календарь", Icons.Default.CalendarMonth,"Planning calendar")
}