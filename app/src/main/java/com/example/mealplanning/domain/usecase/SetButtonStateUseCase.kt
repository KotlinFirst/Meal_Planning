package com.example.mealplanning.domain.usecase

import com.example.mealplanning.domain.repository.PreferencesRepository
import javax.inject.Inject

class SetButtonStateUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository,
) {
    suspend operator fun invoke(isSelected: Boolean) =
        preferencesRepository.setButtonState(isSelected)
}