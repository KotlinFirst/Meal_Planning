package com.example.mealplanning.presentation.screen.recipe

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanning.domain.entity.planAndRecipe.RecipeInformation
import com.example.mealplanning.domain.usecase.GetRecipeInformationUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = RecipeViewModel.Factory::class) // фабрику создаем, если используем Assisted
class RecipeViewModel @AssistedInject constructor(
    private val getRecipeInformationUseCase: GetRecipeInformationUseCase,
    @Assisted("recipeId") private val recipeId: Int,
) : ViewModel() {

    private val _state =
        MutableStateFlow<RecipeState>(RecipeState.Initial)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                val recipeInformation = getRecipeInformationUseCase(recipeId)
                Log.d("toIngredientDbModel", "$recipeInformation")
                RecipeState.ShowingRecipe(recipeInformation)

            }
        }
    }

//    fun processCommand(command: RecipeInformationCommand) {
//
//    }

//    sealed interface RecipeInformationCommand {
//        data class GetRecipe() : RecipeInformationCommand
//    }


    sealed interface RecipeState {
        data object Initial : RecipeState
        data class ShowingRecipe(
            val recipeInformation: RecipeInformation,
        ) : RecipeState

        data object Finished : RecipeState
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("recipeId") recipeId: Int,
        ): RecipeViewModel
    }
}