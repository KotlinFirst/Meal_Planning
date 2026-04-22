package com.example.mealplanning.presentation.screen.milestone

import android.util.Log
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
private val getMealPlaneUseCase: GetMealPlaneUseCase
): ViewModel(){
    private val _state = MutableStateFlow(SearchMealPlanState())

    val state = _state.asStateFlow()

    fun processCommand(command: SearchMealPlanCommand){
        when(command){
            SearchMealPlanCommand.UpdateMealPlan -> {
                viewModelScope.launch {
                    Log.d("MilestoneScreen"," processCommand")
                    _state.update { previousState ->
                        val mealPlan = getMealPlaneUseCase("day",2000)
                        previousState.copy(mealPlan)
                    }
                }
            }
        }
    }
    sealed interface SearchMealPlanCommand{
        data object UpdateMealPlan: SearchMealPlanCommand
    }

    data class SearchMealPlanState(
        val mealPlan: List<MealPlan> = listOf()
    )

}