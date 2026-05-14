package com.example.mealplanning.presentation.screen.recipe

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanning.domain.entity.planAndRecipe.RecipeInformation
import com.example.mealplanning.domain.usecase.DeleteFullRecipeUseCase
import com.example.mealplanning.domain.usecase.GetButtonStateUseCase
import com.example.mealplanning.domain.usecase.GetInstructionsUseCase
import com.example.mealplanning.domain.usecase.GetRecipeInformationUseCase
import com.example.mealplanning.domain.usecase.SaveMealPlanUseCase
import com.example.mealplanning.domain.usecase.SetButtonStateUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = RecipeViewModel.Factory::class) // фабрику создаем, если используем Assisted
class RecipeViewModel @AssistedInject constructor(
    private val getRecipeInformationUseCase: GetRecipeInformationUseCase,
    private val setButtonStateUseCase: SetButtonStateUseCase,
    private val getButtonStateUseCase: GetButtonStateUseCase,
    private val saveMealPlanUseCase: SaveMealPlanUseCase,
    private val getInstructionsUseCase: GetInstructionsUseCase,
    private val deleteFullRecipeUseCase: DeleteFullRecipeUseCase,
    @Assisted("recipeId") private val recipeId: Int,
) : ViewModel() {

    private val _state =
        MutableStateFlow<RecipeState>(RecipeState.Initial)
    val state = _state.asStateFlow()

    private val _saveToggleState = MutableStateFlow(false)
    val saveToggleState = _saveToggleState.asStateFlow()

    fun loadRecipe() {
        viewModelScope.launch {
            _state.update {
                val recipeInformation = getRecipeInformationUseCase(recipeId)
                RecipeState.ShowingRecipe(recipeInformation)
            }
            _saveToggleState.value = getButtonStateUseCase().first()
        }
    }

    fun processCommand(command: RecipeInformationCommand) {
        when (command) {
            is RecipeInformationCommand.IsSelectedSaveRecipeButton -> {

                viewModelScope.launch {
                    when (command.isSelected) {
                        true -> {
                            Log.d("processCommand","command_TRUE")
                            setButtonStateUseCase(true)
                            val currentState = state.value
                            if (currentState is RecipeState.ShowingRecipe) {
                                val listsStep = getInstructionsUseCase(command.recipeId)
                                Log.d("VM","$listsStep, ID${command.recipeId}")
                                saveMealPlanUseCase(
                                    recipe = currentState.recipeInformation,
                                    listsStep = listsStep
                                )
                            }
                            _saveToggleState.update { true }
                        }
                        false -> {
                            Log.d("processCommand","command_FALSE")
                            setButtonStateUseCase(false)
                            deleteFullRecipeUseCase(command.recipeId)
                            _saveToggleState.update { false }
                        }
                    }
                }
            }
        }
    }

    sealed interface RecipeInformationCommand {
        data class IsSelectedSaveRecipeButton(val recipeId: Int, val isSelected: Boolean):
            RecipeInformationCommand
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