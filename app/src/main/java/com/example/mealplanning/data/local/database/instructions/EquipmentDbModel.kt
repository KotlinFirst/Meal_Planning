package com.example.mealplanning.data.local.database.instructions

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "equipment",
    primaryKeys =["equipmentId","recipeId"]
)
data class EquipmentDbModel(
    val equipmentId: Int,
    val recipeId: Int,
    val image: String,
    val localizedName: String,
    val name: String,
    val temperature: Int?,
)
