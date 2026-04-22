package com.example.mealplanning.data.local.database

import android.util.Log
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import com.example.mealplanning.data.local.database.instructions.EquipmentDbModel
import com.example.mealplanning.data.local.database.instructions.InstructionIngredientDbModel
import com.example.mealplanning.data.local.database.instructions.InstructionWithDetailsDbModel
import com.example.mealplanning.data.local.database.instructions.StepDbModel
import com.example.mealplanning.data.local.database.recipe.RecipeIngredientDbModel
import com.example.mealplanning.data.local.database.recipe.RecipeDbModel
import com.example.mealplanning.data.local.database.recipe.RecipeWithIngredientDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MealPlanDao {
    // ADD
//    @Insert(onConflict = IGNORE)
//    suspend fun addMeal(mealPlanDbModel: MealPlanDbModel)

    @Insert(onConflict = IGNORE)
    suspend fun addRecipe(recipeDbModel: RecipeDbModel)

    @Insert(onConflict = IGNORE)
    suspend fun addIngredientsRecipe(listRecipeIngredientDbModel: List<RecipeIngredientDbModel>)


    @Insert(onConflict = IGNORE)
    suspend fun addIngredientsInstruction(listInstructionIngredientDbModel: List<InstructionIngredientDbModel>)

    @Insert(onConflict = IGNORE)
    suspend fun addEquipment(listEquipmentDbModel: List<EquipmentDbModel>)

    @Insert(onConflict = IGNORE)
    suspend fun addStep(listStepDbModel: List<StepDbModel>){
        Log.d("Dao","addStep $listStepDbModel")
    }

    @Transaction
    suspend fun addFullRecipe(
//        mealPlanDbModel: MealPlanDbModel,
        recipeDbModel: RecipeDbModel,
        listRecipeIngredientDbModel: List<RecipeIngredientDbModel>,
        listsInstructionIngredientDbModel: List<List<List<InstructionIngredientDbModel>>>,
        listEquipmentDbModel: List<List<List<EquipmentDbModel>>>,
        listStepDbModel: List<List<StepDbModel>>,
    ) {
        Log.d("DAO","recipeDbModel${recipeDbModel.recipeId}")
        Log.d("DAO","listStepDbModel${listStepDbModel}")
//        addMeal(mealPlanDbModel)
        addRecipe(recipeDbModel)
        addIngredientsRecipe(listRecipeIngredientDbModel)
        listStepDbModel.forEach {
            Log.d("DAO","listStepDbModelFOREACH${it}")
            addStep(it) }
        listsInstructionIngredientDbModel.forEach { listList ->
            listList.forEach { addIngredientsInstruction(it )}
        }
        listEquipmentDbModel.forEach { listList ->
            listList.forEach { addEquipment(it) }
        }

    }

    //GET
//    @Transaction
//    @Query("SELECT * FROM mealPlan")
//    fun getAllSaveMeal(): Flow<List<MealPlanDbModel>>

    @Transaction
    @Query("SELECT*FROM recipe WHERE recipeId == :id")
    fun getRecipe(id: Int): Flow<RecipeDbModel>

    @Transaction
    @Query("SELECT*FROM ingredient_recipe WHERE recipeId == :recipeId")
    fun getRecipeIngredients(recipeId: Int): Flow<RecipeIngredientDbModel>

    @Transaction
    @Query("SELECT*FROM recipe WHERE recipeId = :recipeId")
    fun getRecipeWithIngredient(recipeId: Int): Flow<RecipeWithIngredientDbModel>

    @Transaction
    @Query("SELECT*FROM recipe")
    fun getAllRecipeWithIngredient(): List<RecipeWithIngredientDbModel>

    @Transaction
    @Query("SELECT*FROM step WHERE recipeId =:recipeId")
    fun getRecipeWithEquipment(recipeId: Int): Flow<InstructionWithDetailsDbModel>

    //REMOVE
//    @Delete
//    suspend fun deleteMeal(mealPlanDbModel: MealPlanDbModel)

    @Delete
    suspend fun deleteRecipe(recipeDbModel: RecipeDbModel)

    @Insert(onConflict = IGNORE)
    suspend fun deleteIngredients(listIngredientDbModel: List<RecipeIngredientDbModel>)

    @Transaction
    suspend fun deleteFullRecipe(
//        mealPlanDbModel: MealPlanDbModel,
        recipeDbModel: RecipeDbModel,
        listIngredientDbModel: List<RecipeIngredientDbModel>,
    ) {
//        deleteMeal(mealPlanDbModel)
        deleteRecipe(recipeDbModel)
        deleteIngredients(listIngredientDbModel)
    }
@Transaction
@Query("DELETE FROM recipe WHERE recipeId ==:recipeId")
suspend fun deleteMealById(recipeId: Int){
    Log.d("DAO","REMOVE$recipeId")}

}