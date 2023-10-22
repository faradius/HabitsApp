package com.alex.habitsapp.onboarding.domain.usecase

import com.alex.habitsapp.onboarding.domain.repository.OnboardingRepository

class CompleteOnboardingUseCase(
    private val repository: OnboardingRepository
) {
    operator fun invoke(){
        repository.completeOnboarding()
    }
}