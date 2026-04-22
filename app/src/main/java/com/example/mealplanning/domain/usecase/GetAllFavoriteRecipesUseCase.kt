package com.example.mealplanning.domain.usecase

import com.example.mealplanning.domain.repository.MealPlanRepository
import javax.inject.Inject

class GetAllFavoriteRecipesUseCase @Inject constructor(
    private val mealPlanRepository: MealPlanRepository,
) {
    suspend operator fun invoke() = mealPlanRepository.getAllFavoriteRecipe()
}