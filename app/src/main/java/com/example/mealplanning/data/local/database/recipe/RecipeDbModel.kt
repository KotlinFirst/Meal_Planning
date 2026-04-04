package com.example.mealplanning.data.local.database.recipe

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.mealplanning.data.local.database.MealPlanDbModel

@Entity(
    tableName = "recipe",
    foreignKeys = [
        ForeignKey(
            entity = MealPlanDbModel::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("recipeId")]
)
data class RecipeDbModel(
    @PrimaryKey
    val recipeId: Int,
    val title: String,
    val imageUrl: String,
    val servings: Int,
    val readyInMinutes: Int,
    val cookingMinutes: Int,
//    val ingredients: List<IngredientDbModel>, // этот класс тоже нужно в таблицу
)
