package com.alex.habitsapp.feature.home.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.alex.habitsapp.feature.home.data.extension.toTimeStamp
import com.alex.habitsapp.feature.home.domain.alarm.AlarmHandler
import com.alex.habitsapp.feature.home.domain.model.Habit
import java.time.DayOfWeek
import java.time.ZonedDateTime

class AlarmHandlerImpl(
    private val context: Context
) : AlarmHandler {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun setRecurringAlarm(habit: Habit) {
        val nextOccurrence = calculateNextOccurrence(habit)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            nextOccurrence.toTimeStamp(),
            createPendingIntent(habit, nextOccurrence.dayOfWeek)
        )
    }

    //Se crea la alarma para cualquier habito si son el mismo dia de forma independiente
    private fun createPendingIntent(habit: Habit, dayOfWeek: DayOfWeek): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(AlarmReceiver.HABIT_ID, habit.id)
        }
        return PendingIntent.getBroadcast(
            context,
            habit.id.hashCode() * 10 + dayOfWeek.value,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun calculateNextOccurrence(habit: Habit): ZonedDateTime {
        val today = ZonedDateTime.now()
        var nextOcurrence = ZonedDateTime.of(today.toLocalDate(), habit.reminder, today.zone)
        if (habit.frequency.contains(today.dayOfWeek) && today.isBefore(nextOcurrence)){
            return nextOcurrence
        }
        do {
            nextOcurrence = nextOcurrence.plusDays(1)
        }while (!habit.frequency.contains(nextOcurrence.dayOfWeek))

        return nextOcurrence

    }
    override fun cancel(habit: Habit) {
        val nextOcurrence = calculateNextOccurrence(habit)
        val pending = createPendingIntent(habit, nextOcurrence.dayOfWeek)
        alarmManager.cancel(pending)
    }
}