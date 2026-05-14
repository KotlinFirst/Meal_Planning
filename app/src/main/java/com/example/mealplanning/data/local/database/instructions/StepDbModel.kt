package com.example.mealplanning.data.local.database.instructions

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import com.example.mealplanning.data.local.database.recipe.RecipeDbModel

@Entity(
    tableName = "step",
    primaryKeys = ["recipeId","instructionsId"],
    foreignKeys = [
        ForeignKey(
            entity = RecipeDbModel::class,
            parentColumns = ["recipeId"],
            childColumns = ["recipeId"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("recipeId")]
)
data class StepDbModel(
    val recipeId: Int,
    val instructionsId:Int,
//    val equipment: List<EquipmentDbModel>,
//    val ingredients: List<RecipeIngredientDbModel>,
    val length: Int?,
    val number: Int,
    val step: String,
)
