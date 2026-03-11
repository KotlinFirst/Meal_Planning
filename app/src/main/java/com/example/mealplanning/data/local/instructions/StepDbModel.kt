package com.example.mealplanning.data.local.instructions

import com.example.mealplanning.data.local.recipe.IngredientDbModel

data class StepDbModel(
    val equipment:List<EquipmentDbModel>,
    val ingredients: List<IngredientDbModel>,
    val length: Int?,
    val number: Int,
    val step: String

)
