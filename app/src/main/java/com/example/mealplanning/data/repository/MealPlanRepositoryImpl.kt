package com.example.mealplanning.data.repository

import android.util.Log
import com.example.mealplanning.data.local.database.MealPlanDao
import com.example.mealplanning.data.mapper.toEntity
import com.example.mealplanning.data.mapper.toEquipmentDbModel
import com.example.mealplanning.data.mapper.toInstructionIngredientDbModel
import com.example.mealplanning.data.mapper.toRecipeDbModel
import com.example.mealplanning.data.mapper.toRecipeIngredientDbModel
import com.example.mealplanning.data.mapper.toStepDbModel
import com.example.mealplanning.data.remote.MealPlanApiService
import com.example.mealplanning.data.remote.instructions.StepDto
import com.example.mealplanning.domain.entity.Ingredient
import com.example.mealplanning.domain.entity.instructions.Step
import com.example.mealplanning.domain.entity.planAndRecipe.MealPlan
import com.example.mealplanning.domain.entity.planAndRecipe.RecipeInformation
import com.example.mealplanning.domain.repository.MealPlanRepository
import kotlinx.coroutines.CancellationException
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

//    override fun showSavedMealPlan(): Flow<List<MealPlan>> {
//        return mealPlanDao.getAllSaveMeal().map { listDbModel ->
//            listDbModel.map { mealPlanDbModel ->
//                mealPlanDbModel.toEntity()
//            }
//        }
//    }

    private suspend fun loadInstructions(recipeId: Int): List<List<StepDto>> {
        return try {
            val a = mealPlanApiService.loadInstructions(recipeId).map { it.steps }
            Log.d("loadInstructions", "$a")
            a
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
            listStepDto.map {
                Log.d("getInstructions", "$listStepDto")
                it.toEntity(id = recipeId)
            }
        }
    }

    override suspend fun getAllFavoriteRecipe(): List<RecipeInformation> {
        return mealPlanDao.getAllRecipeWithIngredient().map { it.toEntity() }
    }

    override suspend fun saveMealPlan(
//        mealPlan: MealPlan,
        recipe: RecipeInformation,
        listsStep: List<List<Step>>,
    ) {
        Log.d("IMPL0", "$listsStep")
        mealPlanDao.addFullRecipe(
//            mealPlanDbModel = mealPlan.toMealPlanDbModel(),
            recipeDbModel = recipe.toRecipeDbModel(),
            listRecipeIngredientDbModel = recipe.ingredients.map {
                Log.d("IMPL1", "$it")
                it.toRecipeIngredientDbModel(recipe.recipeId)
            },
            listsInstructionIngredientDbModel = listsStep.map { listStep ->
                listStep.map { step ->
                    step.ingredients.map { it.toInstructionIngredientDbModel(step.recipeId) }
                }
            },
            listEquipmentDbModel = listsStep.map { listStep ->
                listStep.map { step -> step.equipment.map { it.toEquipmentDbModel(step.recipeId) } }
            },
            listStepDbModel = listsStep.mapIndexed { index, listsStep ->
                listsStep.map {
                    Log.d("IMPL", "$it")
                    it.toStepDbModel(index)
                }
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
                it.toRecipeIngredientDbModel(recipe.recipeId)
            }
        )
    }

    override suspend fun deleteMealById(recipeId: Int) {
        mealPlanDao.deleteMealById(recipeId)
    }

}
