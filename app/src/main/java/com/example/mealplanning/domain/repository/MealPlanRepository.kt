package com.example.mealplanning.domain.repository

import com.example.mealplanning.domain.entity.instructions.Instructions
import com.example.mealplanning.domain.entity.instructions.Step
import com.example.mealplanning.domain.entity.planAndRecipe.MealPlan
import com.example.mealplanning.domain.entity.planAndRecipe.RecipeInformation
import kotlinx.coroutines.flow.Flow

interface MealPlanRepository {
    suspend fun getOrUpdateMealPlan(timeFrame:String,targetCalories:Int): List<MealPlan>

    fun showSavedMealPlan(): Flow<List<MealPlan>>

    suspend fun getRecipeInformation(recipeId: Int): RecipeInformation

    suspend fun saveMealPlan(id: Int)

    suspend fun removeMealPlan(id: Int)

    suspend fun getInstructions(recipeId: Int): List<List<Step>>
}