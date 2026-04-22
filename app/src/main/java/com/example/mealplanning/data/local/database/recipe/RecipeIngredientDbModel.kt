package com.example.mealplanning.data.local.database.recipe

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "ingredient_recipe",
    primaryKeys = ["ingredientId","recipeId"]
)
data class RecipeIngredientDbModel(
    val ingredientId:Int,
    val recipeId:Int,
    val imageUri:String,
    val content:String
)