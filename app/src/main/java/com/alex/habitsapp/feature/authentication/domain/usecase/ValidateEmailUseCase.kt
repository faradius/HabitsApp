package com.alex.habitsapp.feature.authentication.domain.usecase

import com.alex.habitsapp.feature.authentication.domain.matcher.EmailMatcher

class ValidateEmailUseCase(private val emailMatcher: EmailMatcher) {
    operator fun invoke(email: String): Boolean {
        return emailMatcher.isValid(email)
    }
}