package com.alex.habitsapp.feature.onboarding.domain.usecase

import com.alex.habitsapp.feature.onboarding.domain.repository.OnboardingRepository

class HasSeenOnboardingUseCase(
    private val repository: OnboardingRepository
) {
    operator fun invoke(): Boolean{
        return repository.hasSeenOnboarding()
    }
}