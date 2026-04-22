package com.example.mealplanning.presentation.screen.favoriteFood

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mealplanning.domain.entity.planAndRecipe.RecipeInformation
import com.example.mealplanning.domain.usecase.GetAllFavoriteRecipesUseCase
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavoriteViewModel @AssistedInject constructor(
    private val getSaveRecipesUseCase: GetAllFavoriteRecipesUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(FavoriteRecipesState())
    val state = _state.asStateFlow()

    fun loadSaveRecipes(){
        viewModelScope.launch {
            _state.update {previousState ->
                val favoriteRecipe = getSaveRecipesUseCase()
                previousState.copy(favoriteRecipe)
            }
        }
    }

    data class FavoriteRecipesState(val favoriteRecipe: List<RecipeInformation> = listOf())
}