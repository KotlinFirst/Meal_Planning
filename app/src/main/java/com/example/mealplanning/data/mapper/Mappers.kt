package com.example.mealplanning.data.mapper

import android.util.Log
import com.example.mealplanning.data.local.database.MealPlanDbModel
import com.example.mealplanning.data.local.database.recipe.IngredientDbModel
import com.example.mealplanning.data.local.database.recipe.RecipeWithIngredientDbModel
import com.example.mealplanning.data.local.database.recipe.RecipeDbModel
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

//DTO.toEntity
fun MealPlanResponseDto.toEntity(): List<MealPlan> {
    return meals.map {
        MealPlan(
            id = it.id,
            imageUri = it.image,
            readyInMinutes = it.readyInMinutes,
            servings = it.servings,
            title = it.title
        )
    }
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

fun EquipmentDto.toEntity(): Equipment {
    return Equipment(
        id = id,
        image = image,
        localizedName = localizedName,
        name = name,
        temperature = temperature?.number
    )
}

fun IngredientDto.toEntity(): Ingredient {
    return Ingredient(
        id = id,
        imageUri = image,
        content = name
    )
}

fun RecipeResponseDto.toEntity(): RecipeInformation{
    return RecipeInformation(
        recipeId = id,
        title = title,
        imageUrl = image,
        servings = servings,
        readyInMinutes = readyInMinutes,
        cookingMinutes = cookingMinutes,
        ingredients = extendedIngredients.map { it.toEntity() }
    )
}

fun ExtendedIngredientDto.toEntity():Ingredient{
    return Ingredient(
        id = id,
        imageUri = image,
        content = original
    )
}

//entity.toDbModel
fun MealPlan.toMealPlanDbModel(): MealPlanDbModel {
    return MealPlanDbModel(
        id = id,
        image = imageUri,
        readyInMinutes = readyInMinutes,
        servings = servings,
        title = title
    )
}
fun RecipeInformation.toRecipeDbModel():RecipeDbModel{
    return RecipeDbModel(
        recipeId = recipeId,
        title = title,
        imageUrl = imageUrl,
        servings = servings,
        readyInMinutes = readyInMinutes,
        cookingMinutes = cookingMinutes
    )
}

fun Ingredient.toIngredientDbModel(recipeId: Int):IngredientDbModel{
    return IngredientDbModel(
        ingredientId = id,
        recipeId = recipeId,
        imageUri = imageUri,
        content = content
    )
}


//DbModel.toEntity
fun MealPlanDbModel.toEntity(): MealPlan {
    return MealPlan(
        id = id,
        imageUri = image,
        title = title,
        readyInMinutes = readyInMinutes,
        servings = servings
    )
}

fun IngredientDbModel.toEntity(): Ingredient {
    return Ingredient(
        id = ingredientId,
        imageUri = imageUri,
        content = content
    )
}

fun RecipeWithIngredientDbModel.toEntity(): RecipeInformation {
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

//temporary
fun RecipeResponseDto.toIngredientWithRecipeDbModel(): RecipeWithIngredientDbModel {
    return RecipeWithIngredientDbModel(
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

fun ExtendedIngredientDto.toIngredientDbModel(recipeId: Int): IngredientDbModel {
    Log.d("toIngredientDbModel", "${recipeId},${id}")
    return IngredientDbModel(
        ingredientId = id,
        recipeId = recipeId,
        imageUri = image,
        content = original
    )
}