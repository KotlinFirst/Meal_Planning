package com.example.mealplanning.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import com.example.mealplanning.data.local.database.recipe.IngredientDbModel
import com.example.mealplanning.data.local.database.recipe.RecipeDbModel
import com.example.mealplanning.data.local.database.recipe.RecipeWithIngredientDbModel
import com.example.mealplanning.domain.entity.instructions.Step
import kotlinx.coroutines.flow.Flow

@Dao
interface MealPlanDao {
    // ADD
    @Insert(onConflict = IGNORE)
    suspend fun addMeal(mealPlanDbModel: MealPlanDbModel)

    @Insert(onConflict = IGNORE)
    suspend fun addRecipe(recipeDbModel: RecipeDbModel)

    @Insert(onConflict = IGNORE)
    suspend fun addAllIngredients(listIngredientDbModel: List<IngredientDbModel>)

    @Transaction
    suspend fun addFullRecipe(
//        mealPlanDbModel: MealPlanDbModel,
        recipeDbModel: RecipeDbModel,
        listsStep: List<List<Step>>,
    ) {
//        addMeal(mealPlanDbModel)
        addRecipe(recipeDbModel)
        addAllIngredients(listIngredientDbModel)
    }

    //GET
    @Query("SELECT * FROM mealPlan")
    fun getAllSaveMeal(): Flow<List<MealPlanDbModel>>

    @Query("SELECT*FROM recipe WHERE recipeId == :id")
    fun getRecipe(id: Int): Flow<RecipeDbModel>

    @Query("SELECT*FROM ingredient WHERE recipeId == :recipeId")
    fun getIngredients(recipeId: Int): Flow<IngredientDbModel>

    @Transaction
    @Query("SELECT*FROM recipe WHERE recipeId = :recipeId")
    fun getRecipeWithIngredient(recipeId: Int): Flow<RecipeWithIngredientDbModel>

    //REMOVE


    @Delete
    suspend fun deleteMeal(mealPlanDbModel: MealPlanDbModel)

    @Delete
    suspend fun deleteRecipe(recipeDbModel: RecipeDbModel)

    @Insert(onConflict = IGNORE)
    suspend fun deleteIngredients(listIngredientDbModel: List<IngredientDbModel>)

    @Transaction
    suspend fun deleteFullRecipe(
//        mealPlanDbModel: MealPlanDbModel,
        recipeDbModel: RecipeDbModel,
        listIngredientDbModel: List<IngredientDbModel>,
    ) {
//        deleteMeal(mealPlanDbModel)
        deleteRecipe(recipeDbModel)
        deleteIngredients(listIngredientDbModel)
    }


}