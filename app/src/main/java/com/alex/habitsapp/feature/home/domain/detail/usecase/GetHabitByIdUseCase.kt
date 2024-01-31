package com.alex.habitsapp.feature.home.domain.detail.usecase

import com.alex.habitsapp.feature.home.domain.model.Habit
import com.alex.habitsapp.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetHabitByIdUseCase(
    private val repository: HomeRepository
) {
    suspend operator fun invoke(id: String): Habit{
        return withContext(Dispatchers.IO){
            repository.getHabitById(id)
        }
    }
}