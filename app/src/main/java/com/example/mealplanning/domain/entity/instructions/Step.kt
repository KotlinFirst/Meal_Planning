package com.example.mealplanning.domain.entity.instructions

import com.example.mealplanning.domain.entity.Ingredient

data class Step(
    val recipeId:Int,
    val equipment:List<Equipment>,
    val ingredients: List<Ingredient>,
    val length: Int?,
    val number: Int,
    val step: String
)
