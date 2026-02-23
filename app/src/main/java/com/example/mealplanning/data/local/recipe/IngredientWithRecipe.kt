package com.example.mealplanning.data.local.recipe

import androidx.room.Embedded
import androidx.room.Relation
import com.example.mealplanning.domain.entity.Ingredient
import com.example.mealplanning.domain.entity.RecipeInformation

data class IngredientWithRecipe(
    @Embedded val recipe: RecipeDbModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val ingredients: List<IngredientDbModel> = listOf()
)
