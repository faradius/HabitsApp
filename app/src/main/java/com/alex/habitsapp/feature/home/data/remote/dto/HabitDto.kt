package com.alex.habitsapp.feature.home.data.remote.dto

data class HabitDto(
    val name: String,
    val reminder: Long,
    val startDate: Long,
    val frequency: List<Int>,
    val completedDates: List<Long>?
)
