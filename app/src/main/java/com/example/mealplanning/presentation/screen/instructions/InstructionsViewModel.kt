package com.example.mealplanning.presentation.screen.instructions

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanning.domain.entity.instructions.Step
import com.example.mealplanning.domain.usecase.GetInstructionsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = InstructionsViewModel.Factory::class)
class InstructionsViewModel @AssistedInject constructor(
    private val getInstructionsUseCase: GetInstructionsUseCase,
    @Assisted("recipeId") private val recipeId: Int,
) : ViewModel() {

    private val _state =
        MutableStateFlow<InstructionsState>(InstructionsState.Initial)

    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            Log.d("VM",recipeId.toString())
            val instructions = getInstructionsUseCase(recipeId)
            Log.d("VM",instructions.toString())
            _state.update {
                    InstructionsState.ShowingRecipe(instructions)
            }
        }
    }

    sealed interface InstructionsState {
        data object Initial : InstructionsState
        data class ShowingRecipe(
            val instructions: List<List<Step>>
        ) : InstructionsState

        data object Finished : InstructionsState
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("recipeId") recipeId: Int,
        ): InstructionsViewModel
    }
}