package com.example.mealplanning.data.remote.instructions


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LengthDto(
    @SerialName("number")
    val number: Int = 0,
    @SerialName("unit")
    val unit: String = ""
)