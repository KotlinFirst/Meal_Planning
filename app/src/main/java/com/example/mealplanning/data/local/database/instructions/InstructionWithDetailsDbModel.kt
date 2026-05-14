package com.example.mealplanning.data.local.database.instructions

import androidx.room.Embedded
import androidx.room.Relation

data class InstructionWithDetailsDbModel(
    @Embedded val step:StepDbModel,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "recipeId"
    ) val equipment: List<EquipmentDbModel>,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "recipeId"
    ) val ingredients: List<InstructionIngredientDbModel>,
)
