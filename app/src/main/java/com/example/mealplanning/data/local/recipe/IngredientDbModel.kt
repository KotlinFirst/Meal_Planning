package com.example.mealplanning.data.local.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "ingredient"
)
data class IngredientDbModel(
    @PrimaryKey
    val ingredientId:Int,
    val recipeId:Int,
    val imageUri:String,
    val content:String
)