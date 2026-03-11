package com.example.mealplanning.presentation.screen.recipe

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.mealplanning.domain.entity.Ingredient
import com.example.mealplanning.presentation.navigation.composable.NavBottomBar
import com.example.mealplanning.presentation.ui.theme.Gray
import kotlin.Int
import kotlin.Unit

@Composable
fun RecipeScreen(
    modifier: Modifier = Modifier,
    recipeId: Int,
    viewModel: RecipeViewModel = hiltViewModel(     //передаем, если у VM есть Assisted
        creationCallback = { factory: RecipeViewModel.Factory ->    //принимает фабрику VMF,возвращает саму VM (VMF) -(VM)
            factory.create(recipeId)
        }
    ),
//    onFinished: () -> Unit,
    onButtonClick: (Int) -> Unit,
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

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    val recipeInf = currentState.recipeInformation
                    Log.d(
                        "RecipeScreen",
                        "https://img.spoonacular.com/recipes/${recipeInf.imageUrl}"
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()

                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .heightIn(max = 250.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.FillWidth,
                            model = recipeInf.imageUrl,
                            contentDescription = null
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .offset(y = (200).dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 32.dp,
                                        topEnd = 32.dp
                                    )
                                )
                                .background(color = MaterialTheme.colorScheme.background)
                        )
                        {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    modifier = modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    text = currentState.recipeInformation.title,
                                    maxLines = 3,
                                    lineHeight = 18.sp,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    modifier = modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    text = "ready in ${currentState.recipeInformation.readyInMinutes} min",
                                    color = Gray
                                )
                                HorizontalDivider()
                                Spacer(Modifier.height(32.dp))
                                LazyColumn {item {
                                    Button(
                                        modifier = Modifier
                                            .padding(horizontal = 24.dp)
                                            .fillMaxWidth(),
                                        onClick = { onButtonClick(currentState.recipeInformation.recipeId) }
                                    ) {
                                        Text(
                                            text = "Стартуем!"
                                        )
                                    }
                                }
                                    items(
                                        items = currentState.recipeInformation.ingredients,
                                    ) {
                                        TableIngredients(it)
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TableIngredients(
    ingredient: Ingredient,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            AsyncImage(
                modifier = Modifier
                    .heightIn(max = 48.dp),
                model = "https://img.spoonacular.com/ingredients_100x100/${ingredient.imageUri}",
                contentDescription = null
            )
        }
        Box(
            modifier = Modifier
                .weight(4f),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = ingredient.content
            )
        }
    }
}

