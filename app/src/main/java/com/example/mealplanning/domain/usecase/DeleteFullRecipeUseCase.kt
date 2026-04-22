package com.example.mealplanning.domain.usecase

import com.example.mealplanning.domain.entity.Ingredient
import com.example.mealplanning.domain.entity.planAndRecipe.RecipeInformation
import com.example.mealplanning.domain.repository.MealPlanRepository
import javax.inject.Inject

class DeleteFullRecipeUseCase @Inject constructor(
    private val mealPlanRepository: MealPlanRepository,
) {
    suspend operator fun invoke(
        recipeId: Int,
    ) = mealPlanRepository.deleteMealById(recipeId)
}