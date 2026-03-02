package com.example.mealplanning.data.remote.instructions


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Step(
    @SerialName("equipment")
    val equipment: List<Equipment> = listOf(),
    @SerialName("ingredients")
    val ingredients: List<Ingredient> = listOf(),
    @SerialName("length")
    val length: Length? = Length(),
    @SerialName("number")
    val number: Int = 0,
    @SerialName("step")
    val step: String = ""
)