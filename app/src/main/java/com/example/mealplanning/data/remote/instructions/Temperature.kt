package com.example.mealplanning.data.remote.instructions


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Temperature(
    @SerialName("number")
    val number: Int = 0,
    @SerialName("unit")
    val unit: String = ""
)