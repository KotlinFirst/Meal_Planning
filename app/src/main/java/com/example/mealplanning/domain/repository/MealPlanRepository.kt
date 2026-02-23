package com.example.mealplanning.domain.repository

import com.example.mealplanning.domain.entity.MealPlan
import com.example.mealplanning.domain.entity.RecipeInformation
import kotlinx.coroutines.flow.Flow

interface MealPlanRepository {
    suspend fun getOrUpdateMealPlan(timeFrame:String,targetCalories:Int): List<MealPlan>

    fun showSavedMealPlan(): Flow<List<MealPlan>>

    suspend fun getRecipeInformation(recipeId: Int): RecipeInformation

    suspend fun saveMealPlan(id: Int)

    suspend fun removeMealPlan(id: Int)
}