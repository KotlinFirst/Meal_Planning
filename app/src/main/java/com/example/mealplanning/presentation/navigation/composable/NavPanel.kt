package com.example.mealplanning.presentation.navigation.composable

import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mealplanning.presentation.navigation.BottomNavPanel

@Composable
fun NavBottomBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    onMealPlanClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onChartClick: () -> Unit,
) {
//    val startScreen = BottomNavPanel.MEAL_PLAN
//    val selectedButtonPanel by rememberSaveable { mutableIntStateOf(startScreen.ordinal) }
    val currentRoute = currentRoute(navController)
    NavigationBar {
        BottomNavPanel.entries.forEach { panel ->
            NavigationBarItem(
                selected = currentRoute == panel.route,
                onClick = {
                    when (panel) {
                        BottomNavPanel.FAVORITE -> onFavoriteClick()
                        BottomNavPanel.MEAL_PLAN -> onMealPlanClick()
                        BottomNavPanel.CHART -> onChartClick()
                    }
                },
                icon = {
                    Icon(
                        imageVector = panel.icon,
                        contentDescription = panel.contentDescription
                    )
                },
                label = {
                    Text(
                        text = panel.label
                    )
                }
            )
        }
    }
    Log.d("NavigationBarFinish","$currentRoute")
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}