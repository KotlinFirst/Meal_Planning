package com.example.mealplanning.presentation.screen.milestone

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealplanning.domain.entity.planAndRecipe.MealPlan
import com.example.mealplanning.domain.usecase.GetMealPlaneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMealPlanViewmodel @Inject constructor(
    private val getMealPlaneUseCase: GetMealPlaneUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<SearchState>(SearchState.Initial)
    val state = _state.asStateFlow()

    init {
        loadData("day", 2000)
    }

    fun processCommand(command: SearchMealPlanCommand) {
        when (command) {
            SearchMealPlanCommand.UpdateMealPlan -> {
                loadData("day", 2000)
            }
        }
    }

    private fun loadData(timeFrame: String, targetCalories: Int) {
        viewModelScope.launch {
            _state.update {
                getMealPlaneUseCase(timeFrame, targetCalories)
                    .takeIf { it.isNotEmpty() }?.let {
                        SearchState.Search(it)
                    } ?: SearchState.Initial
            }
        }
    }


    sealed interface SearchMealPlanCommand {
        data object UpdateMealPlan : SearchMealPlanCommand
    }

    sealed interface SearchState {
        data object Initial : SearchState
        data class Search(
            val mealPlan: List<MealPlan> = listOf(),
        ) : SearchState
    }
}
