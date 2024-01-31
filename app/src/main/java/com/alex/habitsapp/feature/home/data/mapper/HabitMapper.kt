package com.alex.habitsapp.feature.home.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.alex.habitsapp.feature.home.data.extension.toStartOfDateTimestamp
import com.alex.habitsapp.feature.home.data.extension.toTimeStamp
import com.alex.habitsapp.feature.home.data.extension.toZonedDateTime
import com.alex.habitsapp.feature.home.data.local.entity.HabitEntity
import com.alex.habitsapp.feature.home.domain.model.Habit
import java.time.DayOfWeek

@RequiresApi(Build.VERSION_CODES.O)
fun HabitEntity.toDomain(): Habit {
    return Habit(
        id = this.id,
        name = this.name,
        frequency = this.frequency.map { DayOfWeek.of(it) },
        completedDates = this.completedDates.map { it.toZonedDateTime().toLocalDate() },
        reminder = this.reminder.toZonedDateTime().toLocalTime(),
        startDate = this.startDate.toZonedDateTime()
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun Habit.toEntity(): HabitEntity {
    return HabitEntity(
        id = this.id,
        name = this.name,
        frequency = this.frequency.map { it.value },
        completedDates = this.completedDates.map { it.toZonedDateTime().toTimeStamp() },
        reminder = this.reminder.toZonedDateTime().toTimeStamp(),
        startDate = this.startDate.toStartOfDateTimestamp()
    )
}