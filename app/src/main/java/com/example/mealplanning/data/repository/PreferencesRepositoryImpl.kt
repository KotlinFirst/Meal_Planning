package com.example.mealplanning.data.repository

import com.example.mealplanning.data.local.preferences.PreferencesManager
import com.example.mealplanning.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class PreferencesRepositoryImpl @Inject constructor(
    private val preferencesManager: PreferencesManager,
) : PreferencesRepository {
    override val getButtonState: Flow<Boolean> = preferencesManager.buttonStateFlow

    override suspend fun setButtonState(isSelected: Boolean) {
        preferencesManager.setButtonState(isSelected)
    }
}