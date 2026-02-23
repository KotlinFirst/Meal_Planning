package com.example.mealplanning.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import com.example.mealplanning.data.local.recipe.RecipeDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MealPlanDao {

    @Query("SELECT * FROM mealPlan")
    fun getAllSaveMeal(): Flow<List<MealPlanDbModel>>

    @Insert(onConflict = IGNORE)
    suspend fun addMeal(mealPlanDbModel: MealPlanDbModel)

    @Transaction
    @Delete
    suspend fun deleteMeal(mealPlanDbModel: MealPlanDbModel)

    @Query("SELECT*FROM recipe WHERE recipeId == :id")
    fun getRecipe(id: Int): RecipeDbModel

    @Insert(onConflict = IGNORE)
    suspend fun addRecipe(recipeDbModel: RecipeDbModel)

    @Query("DELETE FROM recipe WHERE recipeId == :id")
    suspend fun deleteRecipe(id:Int)
}