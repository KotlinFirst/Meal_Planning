package com.example.mealplanning.domain.usecase

import com.example.mealplanning.domain.repository.MealPlanRepository
import javax.inject.Inject

class GetMealPlaneUseCase @Inject constructor(
    private val mealPlanRepository: MealPlanRepository,
) {
    suspend operator fun invoke(
        timeFrame: String,
        targetCalories: Int,
    ) = mealPlanRepository.getOrUpdateMealPlan(
        timeFrame,
        targetCalories
    )
}