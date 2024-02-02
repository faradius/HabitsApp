package com.alex.habitsapp.feature.home.domain.alarm

import com.alex.habitsapp.feature.home.domain.model.Habit

interface AlarmHandler {
    fun setRecurringAlarm(habit: Habit) //Se va a setear la alarma a partir del habito
    fun cancel(habit: Habit)
}