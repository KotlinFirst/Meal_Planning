package com.example.mealplanning.domain.entity.instructions

data class Equipment(
    val id: Int,
    val image: String,
    val localizedName: String,
    val name: String,
    val temperature: Double?
)
