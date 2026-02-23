@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mealplanning.presentation.screen.milestone

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.mealplanning.domain.entity.MealPlan
import com.example.mealplanning.presentation.navigation.composable.NavBottomBar

@Composable
fun SearchMealPlanScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchMealPlanViewmodel = hiltViewModel(),
) {

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            RecipeTopBar(
                onGetNewRecipe = {
                    viewModel.processCommand(SearchMealPlanViewmodel.SearchMealPlanCommand.UpdateMealPlan)
                    Log.d("MilestoneScreen", " RecipeTopBar")
                }
            )
        }, // обновить список блюд
        bottomBar = { NavBottomBar() } //вкладки 1) рецепты на сегодня 2) запланировать рецепты 3) сохраненые рецепты по дням(ROW списки по дням) и с поиском по названию.
    ) { innerPadding ->
        //val state by viewmodel
        val state by viewModel.state.collectAsState()
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (state.mealPlan.isNotEmpty()) {
                items(
                    items = state.mealPlan,
                    key = { it.imageUri }
                ) {
                    MealCard(mealPlan = it)
                }
            }
        }
    }
}

@Composable
private fun RecipeTopBar(
    modifier: Modifier = Modifier,
    onGetNewRecipe: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text("План питания на день")
        },
        actions = {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onGetNewRecipe()
                        Log.d("MilestoneScreen", "onGetNewRecipe")
                    },
                imageVector = Icons.Default.Refresh,
                contentDescription = "onGetNewRecipe"
            )
        }
    )
}

@Composable
private fun MealCard(
    modifier: Modifier = Modifier,
    mealPlan: MealPlan,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        AsyncImage(
            model = "https://img.spoonacular.com/recipes/${mealPlan.imageUri}",
            contentDescription = null
        )
    }

}

