package com.example.mealplanning.data.remote.instructions


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TemperatureDto(
    @SerialName("number")
    val number: Double = 0.0,
    @SerialName("unit")
    val unit: String = ""
)