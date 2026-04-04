package com.example.mealplanning.presentation.screen.recipe

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanning.domain.entity.planAndRecipe.RecipeInformation
import com.example.mealplanning.domain.usecase.GetRecipeInformationUseCase
import com.example.mealplanning.domain.usecase.SaveMealPlanUseCase
import com.example.mealplanning.domain.usecase.SetButtonStateUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.internal.wait

@HiltViewModel(assistedFactory = RecipeViewModel.Factory::class) // фабрику создаем, если используем Assisted
class RecipeViewModel @AssistedInject constructor(
    private val getRecipeInformationUseCase: GetRecipeInformationUseCase,
    private val setButtonStateUseCase: SetButtonStateUseCase,
    private val saveMealPlanUseCase: SaveMealPlanUseCase,
    @Assisted("recipeId") private val recipeId: Int,
) : ViewModel() {

    private val _state =
        MutableStateFlow<RecipeState>(RecipeState.Initial)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                val recipeInformation = getRecipeInformationUseCase(recipeId)
                RecipeState.ShowingRecipe(recipeInformation)
            }
        }
    }

    fun processCommand(command: RecipeInformationCommand) {
        when (command) {
            is RecipeInformationCommand.IsSelectedButton -> {
                viewModelScope.launch {
                    setButtonStateUseCase(true)
                    val currentState = state.value
                    if (currentState is RecipeState.ShowingRecipe) {
                        saveMealPlanUseCase(
                            recipe = currentState.recipeInformation,
                            listIngredient =
                        )
                    }
                }
            }
        }
    }

    sealed interface RecipeInformationCommand {
        data class IsSelectedButton(val recipeId: Int) : RecipeInformationCommand
    }

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