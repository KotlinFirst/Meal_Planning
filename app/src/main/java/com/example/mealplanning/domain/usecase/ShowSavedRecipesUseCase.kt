package com.example.mealplanning.domain.usecase

import com.example.mealplanning.domain.repository.MealPlanRepository
import javax.inject.Inject

class ShowSavedRecipesUseCase @Inject constructor(
    private val mealPlanRepository: MealPlanRepository,
) {
    operator fun invoke() = mealPlanRepository.showSavedMealPlan()
}