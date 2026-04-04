package com.example.mealplanning.domain.usecase

import com.example.mealplanning.domain.entity.Ingredient
import com.example.mealplanning.domain.entity.instructions.Step
import com.example.mealplanning.domain.entity.planAndRecipe.MealPlan
import com.example.mealplanning.domain.entity.planAndRecipe.RecipeInformation
import com.example.mealplanning.domain.repository.MealPlanRepository
import javax.inject.Inject

class SaveMealPlanUseCase @Inject constructor(
    private val mealPlanRepository: MealPlanRepository,
) {
    suspend operator fun invoke(
//        mealPlan: MealPlan,
        recipe: RecipeInformation,
        listsStep: List<List<Step>>,
    ) = mealPlanRepository.saveMealPlan(
//        mealPlan,
        recipe,
        listsStep
    )
}