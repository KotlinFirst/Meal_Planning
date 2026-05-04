package com.example.mealplanning.domain.usecase

import com.example.mealplanning.domain.repository.MealPlanRepository
import javax.inject.Inject

class GetRecipeInformationUseCase @Inject constructor(
    private val mealPlanRepository: MealPlanRepository,
) {
    suspend operator fun invoke(recipeId:Int) =
        mealPlanRepository.getRecipeInformation(recipeId)
}