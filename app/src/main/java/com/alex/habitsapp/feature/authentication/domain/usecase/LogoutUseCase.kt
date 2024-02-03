package com.alex.habitsapp.feature.authentication.domain.usecase

import com.alex.habitsapp.feature.authentication.domain.repository.AuthenticationRepository

class LogoutUseCase(private val repository: AuthenticationRepository) {
    suspend operator fun invoke(){
        return repository.logout()
    }
}