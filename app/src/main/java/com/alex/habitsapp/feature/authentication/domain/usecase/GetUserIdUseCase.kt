package com.alex.habitsapp.feature.authentication.domain.usecase

import com.alex.habitsapp.feature.authentication.domain.repository.AuthenticationRepository
import javax.inject.Inject

class GetUserIdUseCase @Inject constructor(
    private val repository: AuthenticationRepository
) {
    operator fun invoke(): String? {
        return repository.getUserId()
    }
}