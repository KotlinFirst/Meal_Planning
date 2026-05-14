package com.example.mealplanning.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    val getButtonState: Flow<Boolean>

    suspend fun setButtonState(isSelected: Boolean)
}