package com.example.mealplanning.data.remote.recipe


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExtendedIngredientDto(
    @SerialName("amount")
    val amount: Double = 0.0,
    @SerialName("consistency")
    val consistency: String = "",
    @SerialName("id")
    val id: Int = 0,
    @SerialName("image")
    val image: String = "",
    @SerialName("meta")
    val meta: List<String> = listOf(),
    @SerialName("name")
    val name: String = "",
    @SerialName("original")
    val original: String = "",
//    @SerialName("originalName")
//    val originalName: String = "",
    @SerialName("unit")
    val unit: String = ""
)