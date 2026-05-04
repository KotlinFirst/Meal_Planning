package com.example.mealplanning.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mealplanning.data.local.database.instructions.EquipmentDbModel
import com.example.mealplanning.data.local.database.instructions.InstructionIngredientDbModel
import com.example.mealplanning.data.local.database.instructions.StepDbModel
import com.example.mealplanning.data.local.database.recipe.RecipeIngredientDbModel
import com.example.mealplanning.data.local.database.recipe.RecipeDbModel

@Database(
    entities = [
//        MealPlanDbModel::class,
        RecipeDbModel::class,
        RecipeIngredientDbModel::class,
        InstructionIngredientDbModel::class,
        EquipmentDbModel::class,
        StepDbModel::class],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mealPlanDao(): MealPlanDao

//    companion object {
//        private var instance: MealPlanningDatabase? = null
//
//        private val LOCK = Any()
//
//        fun getInstance(context: Context): MealPlanningDatabase {
//            instance?.let { return it }
//            synchronized(LOCK) {
//                return Room.databaseBuilder(
//                    context = context,
//                    klass = MealPlanningDatabase::class.java, //room под капотом использует JAVA
//                    name = "mealPlanning.db",
//                ).fallbackToDestructiveMigration(dropAllTables = true).build().also {
//                    instance = it
//                }
//            }
//        }
//    }
}