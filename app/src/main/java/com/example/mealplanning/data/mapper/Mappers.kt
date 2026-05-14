package com.example.mealplanning.data.mapper

import android.util.Log
import com.example.mealplanning.data.local.database.MealPlanDbModel
import com.example.mealplanning.data.local.database.instructions.EquipmentDbModel
import com.example.mealplanning.data.local.database.instructions.InstructionIngredientDbModel
import com.example.mealplanning.data.local.database.instructions.InstructionWithDetailsDbModel
import com.example.mealplanning.data.local.database.instructions.StepDbModel
import com.example.mealplanning.data.local.database.recipe.RecipeIngredientDbModel
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

fun StepDto.toEntity(id: Int): Step {
    return Step(
        equipment = equipment.map { it.toEntity() },
        ingredients = ingredients.map { it.toEntity() },
        length = length?.number,
        number = number,
        step = step,
        recipeId = id
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

fun RecipeResponseDto.toEntity(): RecipeInformation {
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

fun ExtendedIngredientDto.toEntity(): Ingredient {
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

fun RecipeInformation.toRecipeDbModel(): RecipeDbModel {
    return RecipeDbModel(
        recipeId = recipeId,
        title = title,
        imageUrl = imageUrl,
        servings = servings,
        readyInMinutes = readyInMinutes,
        cookingMinutes = cookingMinutes
    )
}

fun Ingredient.toRecipeIngredientDbModel(recipeId: Int): RecipeIngredientDbModel {
    return RecipeIngredientDbModel(
        ingredientId = id,
        recipeId = recipeId,
        imageUri = imageUri,
        content = content
    )
}

fun Equipment.toEquipmentDbModel(recipeId: Int): EquipmentDbModel {
    return EquipmentDbModel(
        equipmentId = id,
        recipeId = recipeId,
        image = image,
        localizedName = localizedName,
        name = name,
        temperature = temperature?.toInt() ?: 0
    )
}

fun Ingredient.toInstructionIngredientDbModel(recipeId: Int): InstructionIngredientDbModel {
    return InstructionIngredientDbModel(
        ingredientId = id,
        recipeId = recipeId,
        imageUri = imageUri,
        content = content
    )
}

fun List<Step>.toInstructionWithDetailsDbModel(recipeId: Int): List<InstructionWithDetailsDbModel> {
    return mapIndexed { index, step ->
        InstructionWithDetailsDbModel(
            step = StepDbModel(
                recipeId = recipeId,
                instructionsId = step.recipeId,
                length = step.length,
                number = step.number,
                step = step.step
            ),
            equipment = step.equipment.map { it.toEquipmentDbModel(recipeId) },
            ingredients = step.ingredients.map { it.toInstructionIngredientDbModel(recipeId) }
        )
    }
}

fun Step.toStepDbModel(instructionsId: Int): StepDbModel {
return StepDbModel(
    recipeId = recipeId,
    instructionsId = instructionsId,
    length = length,
    number = number,
    step = step
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

fun RecipeIngredientDbModel.toEntity(): Ingredient {
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
        ingredients = extendedIngredients.map { it.toRecipeIngredientDbModel(this.id) }
    )
}

fun ExtendedIngredientDto.toRecipeIngredientDbModel(recipeId: Int): RecipeIngredientDbModel {
    Log.d("toIngredientDbModel", "${recipeId},${id}")
    return RecipeIngredientDbModel(
        ingredientId = id,
        recipeId = recipeId,
        imageUri = image,
        content = original
    )
}