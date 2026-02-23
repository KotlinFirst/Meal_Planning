package com.example.mealplanning.data.remote.mealPlan


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NutrientsDto(
    @SerialName("calories")
    val calories: Double = 0.0,
    @SerialName("carbohydrates")
    val carbohydrates: Double = 0.0,
    @SerialName("fat")
    val fat: Double = 0.0,
    @SerialName("protein")
    val protein: Double = 0.0
)