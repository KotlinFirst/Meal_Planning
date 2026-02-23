package com.example.mealplanning.presentation.navigation.composable

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.mealplanning.presentation.navigation.BottomNavPanel

@Composable
fun NavBottomBar(
    modifier: Modifier = Modifier,
//    onMilestonePlanClick:() -> Unit,
//    onScheduledMealsClick:() -> Unit
) {
    val startScreen = BottomNavPanel.MEAL_PLAN
    val selectedButtonPanel by rememberSaveable { mutableIntStateOf(startScreen.ordinal) }
    NavigationBar {
        BottomNavPanel.entries.forEachIndexed { index, panel ->
            NavigationBarItem(
                selected = selectedButtonPanel == index,
                onClick = {},
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

}