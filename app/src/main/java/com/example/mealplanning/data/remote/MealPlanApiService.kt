package com.example.mealplanning.data.remote

import com.example.mealplanning.data.remote.mealPlan.MealPlanResponseDto
import com.example.mealplanning.data.remote.recipe.RecipeResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MealPlanApiService {
    @GET("mealplanner/generate?apiKey=b1d4cb85203141ff8f9001ac945fc09d")
    suspend fun loadMealPlan(
        @Query("timeFrame") timeFrame: String, //Either for one "day" or an entire "week"
        @Query("targetCalories") targetCalories: Int,
        ): MealPlanResponseDto

    @GET("recipes/{id}/information?apiKey=b1d4cb85203141ff8f9001ac945fc09d")
    suspend fun loadRecipe(
        @Path("id") recipeId: Int,
    ): RecipeResponseDto

}