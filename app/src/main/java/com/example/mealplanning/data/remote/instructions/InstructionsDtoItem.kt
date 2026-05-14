package com.example.mealplanning.data.remote.instructions


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InstructionsDtoItem(
    @SerialName("name")
    val name: String = "",
    @SerialName("steps")
    val steps: List<StepDto> = listOf()
)