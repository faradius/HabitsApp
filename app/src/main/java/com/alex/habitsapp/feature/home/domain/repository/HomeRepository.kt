package com.alex.habitsapp.feature.home.domain.repository

import com.alex.habitsapp.feature.home.domain.model.Habit
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime


interface HomeRepository {
    fun getAllHabitsForSelectedDate(date: ZonedDateTime): Flow<List<Habit>>
    suspend fun insertHabit(habit: Habit)

    fun getHabitById(id: String): Habit
}