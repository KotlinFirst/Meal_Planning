package com.example.mealplanning.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "mealPlan")
data class MealPlanDbModel(
//    @PrimaryKey
    val id: Int,
    val image: String,
    val readyInMinutes: Int,
    val servings: Int,
    val title: String
)
