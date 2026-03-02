package com.example.mealplanning.data.local.instructions

import com.example.mealplanning.data.local.recipe.IngredientDbModel
import com.example.mealplanning.data.remote.instructions.Equipment
import com.example.mealplanning.data.remote.instructions.Ingredient
import com.example.mealplanning.data.remote.instructions.Length

data class StepDbModel(
    val equipment:List<EquipmentDbModel>,
    val ingredients: List<IngredientDbModel>,
    val length: LengthDbModel?,
    val number: Int,
    val step: String

)
