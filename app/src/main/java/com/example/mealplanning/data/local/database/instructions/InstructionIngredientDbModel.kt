package com.example.mealplanning.data.local.database.instructions

import androidx.room.Entity

@Entity(
    tableName = "ingredient_instruction",
    primaryKeys = ["ingredientId","recipeId"]
)
data class InstructionIngredientDbModel(
    val ingredientId:Int,
    val recipeId:Int,
    val imageUri:String,
    val content:String
)