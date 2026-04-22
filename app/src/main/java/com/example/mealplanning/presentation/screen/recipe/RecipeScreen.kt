package com.example.mealplanning.presentation.screen.recipe

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.mealplanning.domain.entity.Ingredient
import com.example.mealplanning.presentation.navigation.composable.NavBottomBar
import com.example.mealplanning.presentation.ui.theme.Gray

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
    LaunchedEffect(Unit) {
        viewModel.loadRecipe()
    }

    val state = viewModel.state.collectAsState()
    val currentState = state.value
    val saveToggleState = viewModel.saveToggleState.collectAsState().value


    when (currentState) {
        RecipeViewModel.RecipeState.Finished -> {
            LaunchedEffect(key1 = Unit) {
//                onFinished()
            }
        }

        RecipeViewModel.RecipeState.Initial -> {}

        is RecipeViewModel.RecipeState.ShowingRecipe -> {
            Scaffold(
                modifier = modifier,
//                bottomBar = { NavBottomBar() }
            ) { innerPadding ->
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    LazyColumn(
                        Modifier
                            .fillMaxSize()
                    )
                    {
                        item {
                            Box(Modifier.padding(bottom = 18.dp)) {
                                AsyncImage(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(250.dp),
                                    contentScale = ContentScale.FillWidth,
                                    model = currentState.recipeInformation.imageUrl,
                                    contentDescription = null
                                )
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .offset(y = 200.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                topStart = 32.dp,
                                                topEnd = 32.dp
                                            )
                                        )
                                        .background(color = MaterialTheme.colorScheme.background)

                                ) {
                                    Column(Modifier.padding(top = 8.dp)) {
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
                                            maxLines = 1,
                                            text = "ready in ${currentState.recipeInformation.readyInMinutes} min",
                                            color = Gray
                                        )
                                    }
                                }
                            }
                            HorizontalDivider()
                            Spacer(Modifier.height(16.dp))
                        }
                        items(
                            items = currentState.recipeInformation.ingredients,
                        ) {
                            TableIngredients(it)
                        }
                        item { Spacer(Modifier.height(48.dp)) }
                    }
                    Button(
                        modifier = Modifier
                            .widthIn(min = 132.dp)
                            .height(48.dp)
                            .align(Alignment.BottomCenter),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
                        ),
                        onClick = { onButtonClick(currentState.recipeInformation.recipeId) }
                    ) {
                        Text(text = "Стартуем!")
                    }
                    ToggleButton(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        isSelected = saveToggleState,
                        onSaveRecipeButton = {
                            viewModel.processCommand(
                                RecipeViewModel.RecipeInformationCommand.IsSelectedSaveRecipeButton(
                                    recipeId = currentState.recipeInformation.recipeId,
                                    isSelected = it
                                )
                            )
                        },
                    )
//                    ToggleButton(
//                        modifier = Modifier
//                            .align(Alignment.BottomEnd),
//                        iconOn = Icons.Filled.Favorite,
//                        iconOff = Icons.Outlined.FavoriteBorder,
//                        tintOn = MaterialTheme.colorScheme.secondary,
//                        tintOff = MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f),
//                        sizeIsSelected = 154.dp,
//                        sizeNotSelected = 48.dp,
//                    )
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
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(32.dp)
                    .padding(start = 8.dp),
                model = "https://img.spoonacular.com/ingredients_100x100/${ingredient.imageUri}",
                contentDescription = null
            )
        }
        Box(
            modifier = Modifier
                .weight(4f)
                .padding(start = 8.dp),
            contentAlignment = Alignment.CenterStart

        ) {
            Text(
                text = ingredient.content
            )
        }
    }
}

@Composable
fun ToggleButton(
    modifier: Modifier,
    iconOn: ImageVector = Icons.Filled.Favorite,
    iconOff: ImageVector = Icons.Outlined.FavoriteBorder,
    tintOn: Color = MaterialTheme.colorScheme.secondary,
    tintOff: Color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.9f),
    sizeIsSelected: Dp = 32.dp,
    sizeNotSelected: Dp = 24.dp,
    isSelected: Boolean,
    onSaveRecipeButton: (Boolean) -> Unit,
) {

    val tint by animateColorAsState(
        targetValue = if (isSelected) tintOn else tintOff,
        label = "tint"
    )

    val size by animateDpAsState(
        targetValue = if (isSelected) sizeIsSelected else sizeNotSelected,
        label = "size"
    )

    IconToggleButton(
        checked = isSelected,
        onCheckedChange = { onSaveRecipeButton(it) },
        modifier = modifier
    ) {
        Icon(
            imageVector = if (isSelected) iconOn else iconOff,
            contentDescription = if (isSelected) "Сохранено" else "Не сохранено",
            tint = tint,
            modifier = Modifier.size(size)
        )
    }
}
