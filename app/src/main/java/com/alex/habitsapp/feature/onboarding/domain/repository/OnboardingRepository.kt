package com.alex.habitsapp.feature.onboarding.domain.repository

interface OnboardingRepository {
    fun hasSeenOnboarding(): Boolean
    fun completeOnboarding()
}