package com.example.mealplanning.data.local.database.recipe

import androidx.room.Embedded
import androidx.room.Relation

data class RecipeWithIngredientDbModel(
    @Embedded val recipe: RecipeDbModel,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "recipeId"
    )
    val ingredients: List<IngredientDbModel> = listOf()
)
