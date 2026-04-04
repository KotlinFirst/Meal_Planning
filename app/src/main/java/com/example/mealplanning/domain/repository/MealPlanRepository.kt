package com.example.mealplanning.domain.repository

import com.example.mealplanning.domain.entity.Ingredient
import com.example.mealplanning.domain.entity.instructions.Step
import com.example.mealplanning.domain.entity.planAndRecipe.MealPlan
import com.example.mealplanning.domain.entity.planAndRecipe.RecipeInformation
import kotlinx.coroutines.flow.Flow

interface MealPlanRepository {
    suspend fun getOrUpdateMealPlan(timeFrame: String, targetCalories: Int): List<MealPlan>

    fun showSavedMealPlan(): Flow<List<MealPlan>>

    suspend fun getRecipeInformation(recipeId: Int): RecipeInformation

    suspend fun saveMealPlan(
//        mealPlan: MealPlan,
        recipe: RecipeInformation,
        listsStep: List<List<Step>>,
    )

    suspend fun removeMealPlan(
//        mealPlan: MealPlan,
        recipe: RecipeInformation,
        listIngredient: List<Ingredient>,
    )

    suspend fun getInstructions(recipeId: Int): List<List<Step>>
}