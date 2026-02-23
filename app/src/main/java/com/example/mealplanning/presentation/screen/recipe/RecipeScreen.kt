package com.example.mealplanning.presentation.screen.recipe

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.mealplanning.presentation.navigation.composable.NavBottomBar

@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier,
    recipeId: Int,
    viewModel: RecipeViewModel = hiltViewModel(     //передаем, если у VM есть Assisted
        creationCallback = { factory: RecipeViewModel.Factory ->    //принимает фабрику VM,возвращает саму VM (VMF) -(VM)
            factory.create(recipeId)
        }
    ),
    onFinished: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    when (state) {
        RecipeViewModel.RecipeState.Finished -> {
            LaunchedEffect(key1 = Unit) {
                onFinished()
            }
        }

        RecipeViewModel.RecipeState.Initial -> {}

        is RecipeViewModel.RecipeState.ShowingRecipe -> {
            Scaffold(
                modifier = modifier.fillMaxSize(),
                bottomBar = { NavBottomBar() }
            ) { innerPadding ->

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = innerPadding
                ) {
                    item {
                        (state as RecipeViewModel.RecipeState.ShowingRecipe).recipeInformation  //проверить почему требуется доп проверка AS
                        val recipeInf = (state as RecipeViewModel.RecipeState.ShowingRecipe).recipeInformation
                        AsyncImage(
                            model = "https://img.spoonacular.com/recipes/${recipeInf.imageUrl}",
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

