package com.example.mealplanning.data.remote.recipe


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeResponseDto(

    @SerialName("cookingMinutes")
    val cookingMinutes: Int = 0,
    @SerialName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredientDto> = listOf(),
    @SerialName("id")
    val id: Int = 0,
    @SerialName("image")
    val image: String = "",
    @SerialName("language")
    val language: String = "",
    @SerialName("preparationMinutes")
    val preparationMinutes: Int = 0,
    @SerialName("readyInMinutes")
    val readyInMinutes: Int = 0,
    @SerialName("servings")
    val servings: Int = 0,
    @SerialName("spoonacularSourceUrl")
    val spoonacularSourceUrl: String = "",
//    @SerialName("summary")
//    val summary: String = "",
    @SerialName("title")
    val title: String = "",
    @SerialName("vegan")
    val vegan: Boolean = false,
    @SerialName("vegetarian")
    val vegetarian: Boolean = false,
)