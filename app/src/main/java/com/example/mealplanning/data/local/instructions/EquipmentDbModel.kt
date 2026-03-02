package com.example.mealplanning.data.local.instructions

import com.example.mealplanning.data.remote.instructions.Temperature

data class EquipmentDbModel(
    val id: Int,
    val image: String,
    val localizedName: String,
    val name: String,
    val temperature: Temperature?
)
