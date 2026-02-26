package com.example.mealplanning.presentation.screen.recipe

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
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
//    onFinished: () -> Unit,
) {
    val state = viewModel.state.collectAsState()
    val currentState = state.value
    when (currentState) {
        RecipeViewModel.RecipeState.Finished -> {
            LaunchedEffect(key1 = Unit) {
//                onFinished()
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
                        currentState.recipeInformation  //проверить почему требуется доп проверка AS
                        val recipeInf = currentState.recipeInformation
                        Log.d(
                            "RecipeScreen",
                            "https://img.spoonacular.com/recipes/${recipeInf.imageUrl}"
                        )
                        AsyncImage(
                            modifier = Modifier
                                .heightIn(max = 250.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.FillWidth,
                            model = recipeInf.imageUrl,
                            contentDescription = null
                        )
                        Text(
                            text = currentState.recipeInformation.toString()
                        )
                    }
                }
            }
        }
    }
}

