package com.example.mealplanning.domain.entity

data class RecipeInformation (
    val recipeId: Int = 0,
    val title:String = "",
    val imageUrl:String = "",
    val servings:Int = 0,
    val readyInMinutes:Int = 0,
    val cookingMinutes:Int = 0,
    val ingredients:List<Ingredient> = listOf()
)