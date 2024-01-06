package com.alex.habitsapp.feature.authentication.domain.usecase

import com.alex.habitsapp.feature.authentication.domain.repository.AuthenticationRepository

class SignupWithEmailUseCase(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(email: String, password: String):Result<Unit>{
        return repository.signup(email, password)
    }
}