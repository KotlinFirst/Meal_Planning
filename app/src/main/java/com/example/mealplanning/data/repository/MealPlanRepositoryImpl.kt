package com.example.mealplanning.data.repository

import android.util.Log
import com.example.mealplanning.data.local.database.MealPlanDao
import com.example.mealplanning.data.mapper.toEntity
import com.example.mealplanning.data.mapper.toIngredientDbModel
import com.example.mealplanning.data.mapper.toMealPlanDbModel
import com.example.mealplanning.data.mapper.toRecipeDbModel
import com.example.mealplanning.data.remote.MealPlanApiService
import com.example.mealplanning.data.remote.instructions.StepDto
import com.example.mealplanning.domain.entity.Ingredient
import com.example.mealplanning.domain.entity.instructions.Step
import com.example.mealplanning.domain.entity.planAndRecipe.MealPlan
import com.example.mealplanning.domain.entity.planAndRecipe.RecipeInformation
import com.example.mealplanning.domain.repository.MealPlanRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MealPlanRepositoryImpl @Inject constructor(
    private val mealPlanDao: MealPlanDao,
    private val mealPlanApiService: MealPlanApiService,
) : MealPlanRepository {

    private suspend fun loadMealPlan(
        timeFrame: String,
        targetCalories: Int,
    ): List<MealPlan> {
        return try {
            mealPlanApiService.loadMealPlan(timeFrame, targetCalories).toEntity()
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emptyList()
        }
    }


    override suspend fun getOrUpdateMealPlan(
        timeFrame: String,
        targetCalories: Int,
    ): List<MealPlan> {
        return loadMealPlan(
            timeFrame,
            targetCalories
        )
    }

    override fun showSavedMealPlan(): Flow<List<MealPlan>> {
        return mealPlanDao.getAllSaveMeal().map { listDbModel ->
            listDbModel.map { mealPlanDbModel ->
                mealPlanDbModel.toEntity()
            }
        }
    }

    private suspend fun loadInstructions(recipeId: Int): List<List<StepDto>> {
        return try {
            mealPlanApiService.loadInstructions(recipeId).map { it.steps }
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            emptyList()
        }
    }

    private suspend fun loadRecipe(recipeId: Int): RecipeInformation {
        return try {
            mealPlanApiService.loadRecipe(recipeId).toEntity()
        } catch (e: Exception) {
            if (e is CancellationException) {
                Log.d("Exception", e.stackTraceToString())
                throw e
            }
            RecipeInformation()
        }
    }

    override suspend fun getRecipeInformation(recipeId: Int): RecipeInformation {
        return loadRecipe(recipeId)
    }

    override suspend fun getInstructions(recipeId: Int): List<List<Step>> {
        return loadInstructions(recipeId).map { listStepDto ->
            listStepDto.map { it.toEntity() }
        }
    }

    override suspend fun saveMealPlan(
//        mealPlan: MealPlan,
        recipe: RecipeInformation,
        listsStep: List<List<Step>>,
    ) {
        mealPlanDao.addFullRecipe(
//            mealPlan.toMealPlanDbModel(),
            recipe.toRecipeDbModel(),
            listsStep.map {
                it.toIngredientDbModel(recipe.recipeId)
            }
        )
    }

    override suspend fun removeMealPlan(
//        mealPlan: MealPlan,
        recipe: RecipeInformation,
        listIngredient: List<Ingredient>,
    ) {
        mealPlanDao.deleteFullRecipe(
//            mealPlan.toMealPlanDbModel(),
            recipe.toRecipeDbModel(),
            listIngredient.map {
                it.toIngredientDbModel(recipe.recipeId)
            }
        )
    }
}