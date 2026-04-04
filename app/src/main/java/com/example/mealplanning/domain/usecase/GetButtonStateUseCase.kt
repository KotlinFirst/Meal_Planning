package com.example.mealplanning.domain.usecase

import com.example.mealplanning.domain.repository.PreferencesRepository
import javax.inject.Inject

class GetButtonStateUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    operator fun invoke() = preferencesRepository.getButtonState
}