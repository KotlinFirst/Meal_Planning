package com.example.mealplanning.di

import android.content.Context
import androidx.room.Room
import com.example.mealplanning.data.local.database.MealPlanDao
import com.example.mealplanning.data.local.database.AppDatabase
import com.example.mealplanning.data.remote.MealPlanApiService
import com.example.mealplanning.data.repository.MealPlanRepositoryImpl
import com.example.mealplanning.data.repository.PreferencesRepositoryImpl
import com.example.mealplanning.domain.repository.MealPlanRepository
import com.example.mealplanning.domain.repository.PreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

@Singleton
@Binds
fun bindMealPlanRepository(impl: MealPlanRepositoryImpl): MealPlanRepository

@Singleton
@Binds
fun bindPreferenceRepository(impl: PreferencesRepositoryImpl): PreferencesRepository

    companion object {

        @Provides
        @Singleton
        fun provideJson(): Json {
            return Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
            }
        }

        @Provides
        @Singleton
        fun provideConverterFactory(
            json: Json,
        ): Converter.Factory {
            return json.asConverterFactory("application/json".toMediaType())
        }

        @Provides
        @Singleton
        fun provideRetrofit(converter: Converter.Factory): Retrofit {
            return Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com/")
                .addConverterFactory(converter)
                .build()
        }

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): MealPlanApiService {
            return retrofit.create<MealPlanApiService>()
        }

        @Provides
        @Singleton
        fun provideMealPlanningDatabase(
            @ApplicationContext context: Context,
        ): AppDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = AppDatabase::class.java,
                name = "mealPlanning.db"
            ).build()
//                .fallbackToDestructiveMigration(dropAllTables = true).build()

        }

//        @Provides
//        @Singleton
//        fun provideMealPlanningDatabase(
//            @ApplicationContext context: Context
//        ):MealPlanningDatabase{
//            return MealPlanningDatabase.getInstance(context)
//        }

        @Provides
        @Singleton
        fun providesMealPlanDao(
            database: AppDatabase,
        ): MealPlanDao {
            return database.mealPlanDao()
        }
    }
}