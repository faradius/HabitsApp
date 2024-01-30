package com.alex.habitsapp.feature.home.domain.detail.usecase

import com.alex.habitsapp.feature.home.domain.model.Habit
import com.alex.habitsapp.feature.home.domain.repository.HomeRepository
import java.time.ZonedDateTime

class InsertHabitUseCase(private val repository: HomeRepository) {
    suspend operator fun invoke(habit: Habit) {
        repository.insertHabit(habit)
    }
}