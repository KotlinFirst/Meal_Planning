package com.example.mealplanning.data.local.database.instructions

import com.example.mealplanning.data.local.database.recipe.IngredientDbModel

data class StepDbModel(
    val equipment:List<EquipmentDbModel>,
    val ingredients: List<IngredientDbModel>,
    val length: Int?,
    val number: Int,
    val step: String
)
