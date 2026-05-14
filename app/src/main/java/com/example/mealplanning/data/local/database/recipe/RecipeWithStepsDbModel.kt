package com.example.mealplanning.data.local.database.recipe

import androidx.room.Embedded
import androidx.room.Relation
import com.example.mealplanning.data.local.database.instructions.StepDbModel

data class RecipeWithStepsDbModel(
    @Embedded val recipe: RecipeDbModel,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "id"
    ) val step: List<StepDbModel>,
)