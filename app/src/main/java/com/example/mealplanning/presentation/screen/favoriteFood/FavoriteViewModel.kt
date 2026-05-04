package com.example.mealplanning.presentation.screen.favoriteFood

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanning.domain.entity.planAndRecipe.RecipeInformation
import com.example.mealplanning.domain.usecase.DeleteFullRecipeUseCase
import com.example.mealplanning.domain.usecase.GetAllFavoriteRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getSaveRecipesUseCase: GetAllFavoriteRecipesUseCase,
    private val deleteFullRecipeUseCase: DeleteFullRecipeUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(FavoriteRecipesState())
    val state = _state.asStateFlow()
    //    fun loadSaveRecipes(){
//        viewModelScope.launch {
//            _state.update {previousState ->
//                val favoriteRecipe = getSaveRecipesUseCase()
//                previousState.copy(favoriteRecipe)
//            }
//        }
//    }
//    init {
//        viewModelScope.launch {
//            getSaveRecipesUseCase().collect { recipes ->
//                _state.update {
//                    it.copy(recipes)
//                }
//            }
//        }
//    }

    init {
        getSaveRecipesUseCase().onEach { recipes ->
            _state.update {
                it.copy(recipes)
            }
            Log.d("INIT_FavoriteViewModel", "$recipes")
        }.launchIn(viewModelScope)
    }

    fun processCommand(command: FavoriteCommand) {
        viewModelScope.launch {
            when (command) {
                is FavoriteCommand.LongClickCard -> {
                    deleteFullRecipeUseCase(command.recipeId)
                }
            }
        }
    }

    sealed interface FavoriteCommand {
        data class LongClickCard(
            val recipeId: Int,
        ) : FavoriteCommand
    }


    data class FavoriteRecipesState(val favoriteRecipe: List<RecipeInformation> = listOf())
}