package com.alex.habitsapp.feature.home.domain.home.usecase

import com.alex.habitsapp.feature.home.domain.repository.HomeRepository

class SyncHabitUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke() {
        repository.syncHabits()
    }
}