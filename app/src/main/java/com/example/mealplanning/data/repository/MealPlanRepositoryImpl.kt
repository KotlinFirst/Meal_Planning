package com.example.mealplanning.data.repository

import android.util.Log
import com.example.mealplanning.data.local.MealPlanDao
import com.example.mealplanning.data.local.MealPlanDbModel
import com.example.mealplanning.data.local.recipe.RecipeDbModel
import com.example.mealplanning.data.mapper.toEntity
import com.example.mealplanning.data.mapper.toMealPlanDbModel
import com.example.mealplanning.data.mapper.toRecipeDbModel
import com.example.mealplanning.data.remote.MealPlanApiService
import com.example.mealplanning.domain.entity.MealPlan
import com.example.mealplanning.domain.entity.RecipeInformation
import com.example.mealplanning.domain.repository.MealPlanRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealPlanRepositoryImpl @Inject constructor(
    private val mealPlanDao: MealPlanDao,
    private val mealPlanApiService: MealPlanApiService,
) : MealPlanRepository {

    private suspend fun loadMealPlan(
        timeFrame: String,
        targetCalories: Int,
    ): List<MealPlanDbModel> {
        return try {
            mealPlanApiService.loadMealPlan(timeFrame, targetCalories).toMealPlanDbModel()
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            Log.e("MealPlanRepositoryImpl", e.stackTraceToString())
            listOf()
        }
    }


    override suspend fun getOrUpdateMealPlan(
        timeFrame: String,
        targetCalories: Int,
    ): List<MealPlan> {
        return loadMealPlan(
            timeFrame,
            targetCalories
        ).map { it.toEntity() }
    }

    override fun showSavedMealPlan(): Flow<List<MealPlan>> {
        TODO("Not yet implemented")
    }

    private suspend fun loadRecipe(recipeId: Int): RecipeDbModel {
        return try {
            mealPlanApiService.loadRecipe(recipeId).toRecipeDbModel()
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            RecipeDbModel(
                recipeId = 0,
                title = "Ошибка загрузки",
                imageUrl = "",
                servings = 0,
                readyInMinutes = 0,
                cookingMinutes = 0
            )
        }
    }

    override suspend fun getRecipeInformation(recipeId: Int): RecipeInformation {
        return loadRecipe(recipeId)
    }

    override suspend fun saveMealPlan(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun removeMealPlan(id: Int) {
        TODO("Not yet implemented")
    }
}