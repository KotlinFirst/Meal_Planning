package com.example.mealplanning.data.local.instructions

data class EquipmentDbModel(
    val id: Int,
    val image: String,
    val localizedName: String,
    val name: String,
    val temperature: Int?
)
