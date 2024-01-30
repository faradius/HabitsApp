package com.alex.habitsapp.feature.home.domain.detail.usecase

import com.alex.habitsapp.feature.home.domain.model.Habit
import com.alex.habitsapp.feature.home.domain.repository.HomeRepository

class GetHabitByIdUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(id: String): Habit{
        return repository.getHabitById(id)
    }
}