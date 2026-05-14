@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.mealplanning.presentation.screen.milestone

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.mealplanning.domain.entity.planAndRecipe.MealPlan

@Composable
fun SearchMealPlanScreen(
    modifier: Modifier = Modifier,
    onRecipeClick: (MealPlan) -> Unit,
    viewModel: SearchMealPlanViewmodel = hiltViewModel(),
) {
//    viewModel.processCommand(SearchMealPlanViewmodel.SearchMealPlanCommand.UpdateMealPlan)
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            RecipeTopBar(
                onGetNewRecipe = {
                    viewModel.processCommand(SearchMealPlanViewmodel.SearchMealPlanCommand.UpdateMealPlan)
                    Log.d("MilestoneScreen", " RecipeTopBar")
                }
            )
        }
    ) { innerPadding ->
        val state = viewModel.state.collectAsState()
        val currentState = state.value
        when (currentState) {
            SearchMealPlanViewmodel.SearchState.Initial -> {
                Log.d("SearchMealPlanScreen", "Initial")
                Box(modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp)
//                            .fillMaxWidth()
//                            .padding(horizontal = 16.dp)
                    )
                }
            }

            is SearchMealPlanViewmodel.SearchState.Search -> {
                Log.d("SearchMealPlanScreen", "Search")
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    contentPadding = innerPadding,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (currentState.mealPlan.isNotEmpty()) {
                        items(
                            items = currentState.mealPlan,
                            key = { it.imageUri }
                        ) {
                            MealCard(mealPlan = it, onRecipeClick = onRecipeClick)
                        }
                    }
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
    onRecipeClick: (MealPlan) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    onRecipeClick(mealPlan)
                    Log.d("Milestone_Screen", "MealCard_Click")
                },
                onLongClick = { onRecipeClick(mealPlan) }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .heightIn(max = 180.dp)
                    .fillMaxWidth(),
                model = "https://img.spoonacular.com/recipes/${mealPlan.imageUri}",
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
            Log.d("AsyncImage", mealPlan.imageUri)
            Column(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.onSurface
                            )
                        )
                    )
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = "${mealPlan.readyInMinutes} мин.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.surface,
                )
                Text(
                    text = mealPlan.title,
                    maxLines = 2,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}

