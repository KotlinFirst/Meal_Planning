package com.example.mealplanning.domain.entity

data class MealPlan (
    val id: Int,
    val imageUri: String,
    val title:String,
    val readyInMinutes:Int,
    val servings: Int
)