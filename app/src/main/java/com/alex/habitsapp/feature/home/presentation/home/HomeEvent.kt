package com.alex.habitsapp.feature.home.presentation.home

import com.alex.habitsapp.feature.home.domain.model.Habit
import java.time.ZonedDateTime

sealed interface HomeEvent {
   data class ChangeDate(val date: ZonedDateTime) : HomeEvent
    data class CompleteHabit(val habit: Habit) : HomeEvent
}