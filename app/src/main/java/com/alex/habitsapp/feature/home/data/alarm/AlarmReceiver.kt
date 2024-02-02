package com.alex.habitsapp.feature.home.data.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.alex.habitsapp.R
import com.alex.habitsapp.feature.home.data.extension.goAsync
import com.alex.habitsapp.feature.home.domain.alarm.AlarmHandler
import com.alex.habitsapp.feature.home.domain.model.Habit
import com.alex.habitsapp.feature.home.domain.repository.HomeRepository
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {
    companion object {
        const val HABIT_ID = "habit_id"
        const val CHANNEL_ID = "habit_channel"
    }
    //El broadcast receiver no se puede inyectar el repositorio no lo permite por lo que se hace por atributo
    @Inject
    lateinit var repository: HomeRepository

    @Inject
    lateinit var alarmHandler: AlarmHandler
    override fun onReceive(context: Context?, intent: Intent?) = goAsync{
        if (context == null || intent == null) return@goAsync
        val id = intent.getStringExtra(HABIT_ID) ?: return@goAsync
        val habit = repository.getHabitById(id)
        Log.d("TAG", "Llegamos a la notificación de ${habit.name}")
        createNotificationChannel(context)
        if (!habit.completedDates.contains(LocalDate.now())){
            showNotification(context, habit)
        }
        alarmHandler.setRecurringAlarm(habit)
    }

    private fun showNotification(context: Context, habit: Habit) {
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(habit.name)
            .setSmallIcon(R.drawable.notification_icon)
            .setAutoCancel(true) // al presionar la notificacion se cierra
            .build()
        notificationManager.notify(habit.id.hashCode(), notification)
    }

    private fun createNotificationChannel(context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //Crear el canal de notificaciones
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Habit Reminder Channel",
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.description = "Get your habits reminder!"
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}