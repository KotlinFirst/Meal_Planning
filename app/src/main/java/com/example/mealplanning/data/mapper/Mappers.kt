package com.example.mealplanning.data.mapper

import android.util.Log
import com.example.mealplanning.data.local.MealPlanDbModel
import com.example.mealplanning.data.local.recipe.IngredientDbModel
import com.example.mealplanning.data.local.recipe.IngredientWithRecipe
import com.example.mealplanning.data.local.recipe.RecipeDbModel
import com.example.mealplanning.data.remote.instructions.EquipmentDto
import com.example.mealplanning.data.remote.instructions.IngredientDto
import com.example.mealplanning.data.remote.instructions.StepDto
import com.example.mealplanning.data.remote.mealPlan.MealPlanResponseDto
import com.example.mealplanning.data.remote.recipe.ExtendedIngredientDto
import com.example.mealplanning.data.remote.recipe.RecipeResponseDto
import com.example.mealplanning.domain.entity.Ingredient
import com.example.mealplanning.domain.entity.instructions.Equipment
import com.example.mealplanning.domain.entity.instructions.Step
import com.example.mealplanning.domain.entity.planAndRecipe.MealPlan
import com.example.mealplanning.domain.entity.planAndRecipe.RecipeInformation

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

//fun ExtendedIngredientDto.toIngredientDbModel(recipeId:Int): IngredientDbModel {
//    return IngredientDbModel(
//        ingredientId = id,
//        imageUri = image,
//        content = original,
//        recipeId = recipeId
//    )
//}

fun IngredientWithRecipe.toEntity(): RecipeInformation {
    Log.d("toIngredientDbModel", "${recipe.recipeId},${ingredients.map { it.ingredientId }}")
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

fun ExtendedIngredientDto.toIngredientDbModel(recipeId: Int): IngredientDbModel {
    Log.d("toIngredientDbModel", "${recipeId},${id}")
    return IngredientDbModel(
        ingredientId = id,
        recipeId = recipeId,
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

fun IngredientDto.toDbModel(recipeId: Int): IngredientDbModel {
    return IngredientDbModel(
        ingredientId = id,
        recipeId = recipeId,
        imageUri = image,
        content = name
    )
}

fun IngredientDto.toEntity(): Ingredient {
    return Ingredient(
        id = id,
        imageUri = image,
        content = name
    )
}

fun EquipmentDto.toEntity(): Equipment {
    return Equipment(
        id = id,
        image = image,
        localizedName = localizedName,
        name = name,
        temperature = temperature?.number
    )
}

fun StepDto.toEntity(): Step {
    return Step(
        equipment = equipment.map { it.toEntity() },
        ingredients = ingredients.map { it.toEntity() },
        length = length?.number,
        number = number,
        step = step
    )
}