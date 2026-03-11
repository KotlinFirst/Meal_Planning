package com.example.mealplanning.domain.usecase

import android.util.Log
import com.example.mealplanning.domain.repository.MealPlanRepository
import javax.inject.Inject

class GetInstructionsUseCase @Inject constructor(
    private val mealPlanRepository: MealPlanRepository
) {
    suspend operator fun invoke(
        recipeId: Int
    ) =mealPlanRepository.getInstructions(recipeId)
}