package com.example.mealplanning.presentation.screen.favoriteFood

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.mealplanning.domain.entity.planAndRecipe.RecipeInformation

@Composable
fun FavoriteRecipesScreen(
    modifier: Modifier = Modifier,
    onRecipeClick: (RecipeInformation) -> Unit,
    viewModel: FavoriteViewModel = hiltViewModel(),
) {
//    LaunchedEffect(Unit) { viewModel.loadSaveRecipes() }
    Scaffold(
        modifier = modifier.fillMaxSize(),
//        bottomBar = { NavBottomBar() }
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
            if (state.favoriteRecipe.isNotEmpty()) {
                items(
                    items = state.favoriteRecipe,
                    key = { it.recipeId }
                ) { recipeInformation ->
                    MealCard(
                        recipeInformation = recipeInformation,
                        onRecipeClick = { onRecipeClick(recipeInformation) },
                        onLongClick = {
                            Log.d("onLongClick","Click!")
                            viewModel.processCommand(
                                FavoriteViewModel.FavoriteCommand.LongClickCard(
                                    recipeInformation.recipeId
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MealCard(
    modifier: Modifier = Modifier,
    recipeInformation: RecipeInformation,
    onRecipeClick: (RecipeInformation) -> Unit,
    onLongClick: (Int) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    onRecipeClick(recipeInformation)
                    Log.d("Milestone_Screen", "MealCard_Click")
                },
                onLongClick = { onLongClick(recipeInformation.recipeId) }
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .heightIn(max = 180.dp)
                    .fillMaxWidth(),
                model = recipeInformation.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
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
                    text = "${recipeInformation.readyInMinutes} мин.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.surface,
                )
                Text(
                    text = recipeInformation.title,
                    maxLines = 2,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.surface
                )

            }
        }

    }

}