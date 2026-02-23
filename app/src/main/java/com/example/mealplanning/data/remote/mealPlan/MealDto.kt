package com.example.mealplanning.data.remote.mealPlan


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MealDto(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("image")
    val image: String = "",
    @SerialName("readyInMinutes")
    val readyInMinutes: Int = 0,
    @SerialName("servings")
    val servings: Int = 0,
    @SerialName("title")
    val title: String = ""
)