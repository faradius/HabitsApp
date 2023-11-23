package com.alex.habitsapp.feature.onboarding.domain.usecase

import com.alex.habitsapp.feature.onboarding.domain.repository.OnboardingRepository

class CompleteOnboardingUseCase(
    private val repository: OnboardingRepository
) {
    operator fun invoke(){
        repository.completeOnboarding()
    }
}