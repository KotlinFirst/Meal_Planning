package com.example.mealplanning.data.remote.mealPlan


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealPlanResponseDto(
    @SerialName("meals")
    val meals: List<MealDto> = listOf(),
    @SerialName("nutrients")
    val nutrients: NutrientsDto = NutrientsDto()
)