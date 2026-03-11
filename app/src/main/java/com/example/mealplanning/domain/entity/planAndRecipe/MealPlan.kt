package com.example.mealplanning.domain.entity.planAndRecipe

data class MealPlan (
    val id: Int,
    val imageUri: String,
    val title:String,
    val readyInMinutes:Int,
    val servings: Int
)