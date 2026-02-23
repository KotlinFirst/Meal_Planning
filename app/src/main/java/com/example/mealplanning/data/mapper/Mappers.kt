package com.example.mealplanning.data.mapper

import com.example.mealplanning.data.local.recipe.IngredientDbModel
import com.example.mealplanning.data.local.MealPlanDbModel
import com.example.mealplanning.data.local.recipe.IngredientWithRecipe
import com.example.mealplanning.data.local.recipe.RecipeDbModel
import com.example.mealplanning.data.remote.mealPlan.MealPlanResponseDto
import com.example.mealplanning.data.remote.recipe.ExtendedIngredient
import com.example.mealplanning.data.remote.recipe.RecipeResponseDto
import com.example.mealplanning.domain.entity.Ingredient
import com.example.mealplanning.domain.entity.MealPlan
import com.example.mealplanning.domain.entity.RecipeInformation

fun MealPlanResponseDto.toMealPlanDbModel(): List<MealPlanDbModel> {
    return meals.map {
        MealPlanDbModel(
            id = it.id,
            image = it.image,
            readyInMinutes = it.readyInMinutes,
            servings = it.servings,
            title = it.title
        )
    }
}

fun MealPlan.toMealPlanDbModel(): MealPlanDbModel {
    return MealPlanDbModel(
        id = id,
        image = imageUri,
        readyInMinutes = readyInMinutes,
        servings = servings,
        title = title
    )
}

fun MealPlanDbModel.toEntity(): MealPlan {
    return MealPlan(
        id = id,
        imageUri = image,
        title = title,
        readyInMinutes = readyInMinutes,
        servings = servings
    )

}

fun RecipeResponseDto.toIngredientWithRecipe(): IngredientWithRecipe {
    return IngredientWithRecipe(
        recipe = RecipeDbModel(
            recipeId = id,
            title = title,
            imageUrl = image,
            servings = servings,
            readyInMinutes = readyInMinutes,
            cookingMinutes = cookingMinutes
        ),
        ingredients = extendedIngredients.map { it.toIngredientDbModel(this.id) }
    )
}

fun RecipeResponseDto.toRecipeDbModel(): RecipeDbModel {
    return RecipeDbModel(
        recipeId = id,
        title = title,
        imageUrl = spoonacularSourceUrl,
        servings = servings,
        readyInMinutes = readyInMinutes,
        cookingMinutes = cookingMinutes,
//        ingredients = extendedIngredients.map { it.toIngredientDbModel(id) }
    )
}

fun RecipeResponseDto.toIngredientDbModel(): List<IngredientDbModel> {
    val recipeId = id
    return extendedIngredients.map {
        IngredientDbModel(
            ingredientId = it.id,
            recipeId = recipeId,
            imageUri = it.image,
            content = it.original
        )
    }
}

fun RecipeResponseDto.toDbModels(): Pair<RecipeDbModel, List<IngredientDbModel>> {
    return toRecipeDbModel() to toIngredientDbModel()
}

//fun ExtendedIngredient.toIngredientDbModel(recipeId:Int): IngredientDbModel {
//    return IngredientDbModel(
//        ingredientId = id,
//        imageUri = image,
//        content = original,
//        recipeId = recipeId
//    )
//}

fun IngredientWithRecipe.toEntity(): RecipeInformation {
    return RecipeInformation(
        recipeId = recipe.recipeId,
        title = recipe.title,
        imageUrl = recipe.imageUrl,
        servings = recipe.servings,
        readyInMinutes = recipe.readyInMinutes,
        cookingMinutes = recipe.cookingMinutes,
        ingredients = ingredients.map { it.toEntity() }
    )
}

fun ExtendedIngredient.toIngredientDbModel(ingredientId: Int): IngredientDbModel {
    return IngredientDbModel(
        ingredientId = ingredientId,
        recipeId = id,
        imageUri = image,
        content = original
    )
}

fun IngredientDbModel.toEntity(): Ingredient {
    return Ingredient(
        id = ingredientId,
        imageUri = imageUri,
        content = content
    )
}