package com.example.mealplanning.data.local.database.recipe

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipe",
//    foreignKeys = [
//        ForeignKey(
//            entity = MealPlanDbModel::class,
//            parentColumns = ["id"],
//            childColumns = ["recipeId"],
//            onDelete = CASCADE
//        )
//    ],
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
//    val ingredients: List<RecipeIngredientDbModel>, // этот класс тоже нужно в таблицу
)
