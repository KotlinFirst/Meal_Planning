package com.example.mealplanning.data.remote.instructions


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StepDto(
    @SerialName("equipment")
    val equipment: List<EquipmentDto> = listOf(),
    @SerialName("ingredients")
    val ingredients: List<IngredientDto> = listOf(),
    @SerialName("length")
    val length: LengthDto? = LengthDto(),
    @SerialName("number")
    val number: Int = 0,
    @SerialName("step")
    val step: String = ""
)