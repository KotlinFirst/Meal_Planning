package com.example.mealplanning.data.repository

import android.util.Log
import com.example.mealplanning.data.local.MealPlanDao
import com.example.mealplanning.data.local.MealPlanDbModel
import com.example.mealplanning.data.local.recipe.IngredientWithRecipe
import com.example.mealplanning.data.local.recipe.RecipeDbModel
import com.example.mealplanning.data.mapper.toDbModel
import com.example.mealplanning.data.mapper.toEntity
import com.example.mealplanning.data.mapper.toIngredientWithRecipe
import com.example.mealplanning.data.mapper.toMealPlanDbModel
import com.example.mealplanning.data.remote.MealPlanApiService
import com.example.mealplanning.data.remote.instructions.InstructionsListDto
import com.example.mealplanning.data.remote.instructions.StepDto
import com.example.mealplanning.domain.entity.instructions.Step
import com.example.mealplanning.domain.entity.planAndRecipe.MealPlan
import com.example.mealplanning.domain.entity.planAndRecipe.RecipeInformation
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

    private suspend fun loadInstructions(recipeId: Int): List<List<StepDto>> {
        return try {
            Log.d("TEST3", mealPlanApiService.loadInstructions(recipeId).toString())
            mealPlanApiService.loadInstructions(recipeId).map { it.steps }
        } catch (e: Exception) {
            Log.d("TEST3", e.stackTraceToString())
            if (e is CancellationException) {
                throw e

            }
            listOf()
        }
    }

    private suspend fun loadRecipe(recipeId: Int): IngredientWithRecipe {
        return try {
            mealPlanApiService.loadRecipe(recipeId).toIngredientWithRecipe()
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            IngredientWithRecipe(

                recipe = RecipeDbModel(
                    recipeId = 0,
                    title = "Ошибка загрузки",
                    imageUrl = "",
                    servings = 0,
                    readyInMinutes = 0,
                    cookingMinutes = 0
                )
            )
        }
    }

    override suspend fun getRecipeInformation(recipeId: Int): RecipeInformation {
        return loadRecipe(recipeId).toEntity()
    }

    override suspend fun getInstructions(recipeId: Int): List<List<Step>> {

        return loadInstructions(recipeId).map { listStepDto ->
            listStepDto.map { it.toEntity() }
        }
    }

    override suspend fun saveMealPlan(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun removeMealPlan(id: Int) {
        TODO("Not yet implemented")
    }


}