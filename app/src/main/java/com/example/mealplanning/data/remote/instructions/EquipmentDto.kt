package com.example.mealplanning.data.remote.instructions


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EquipmentDto(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("image")
    val image: String = "",
    @SerialName("localizedName")
    val localizedName: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("temperature")
    val temperature: TemperatureDto? = TemperatureDto()
)