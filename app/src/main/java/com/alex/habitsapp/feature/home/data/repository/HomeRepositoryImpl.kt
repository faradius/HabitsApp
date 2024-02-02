package com.alex.habitsapp.feature.home.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.alex.habitsapp.feature.home.data.extension.toStartOfDateTimestamp
import com.alex.habitsapp.feature.home.data.local.HomeDao
import com.alex.habitsapp.feature.home.data.mapper.toDomain
import com.alex.habitsapp.feature.home.data.mapper.toDto
import com.alex.habitsapp.feature.home.data.mapper.toEntity
import com.alex.habitsapp.feature.home.data.remote.HomeApi
import com.alex.habitsapp.feature.home.data.remote.util.resultOf
import com.alex.habitsapp.feature.home.domain.alarm.AlarmHandler
import com.alex.habitsapp.feature.home.domain.model.Habit
import com.alex.habitsapp.feature.home.domain.repository.HomeRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.lang.Exception
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
class HomeRepositoryImpl(
    private val dao: HomeDao,
    private val api: HomeApi,
    private val alarmHandler: AlarmHandler
) : HomeRepository {


    override fun getAllHabitsForSelectedDate(date: ZonedDateTime): Flow<List<Habit>> {
        val localFlow = dao.getAllHabitsForSelectedDate(date.toStartOfDateTimestamp())
            .map { it.map { it.toDomain() } }
        val apiFlow = getHabitsFromApi()

        return localFlow.combine(apiFlow) { db, _ ->
            db
        }
    }

    private fun getHabitsFromApi(): Flow<List<Habit>> {
        return flow {
            resultOf {
                val habits = api.getAllHabits().toDomain()
                insertHabits(habits)
            }
            emit(emptyList<Habit>())
        }.onStart {
            emit(emptyList())
        }
    }


    override suspend fun insertHabit(habit: Habit) {
        handleAlarm(habit)
        dao.insertHabit(habit.toEntity())
        resultOf {
            api.insertHabit(habit.toDto())
        }
    }

    private suspend fun insertHabits(habits: List<Habit>) {
        habits.forEach{
            handleAlarm(it)
            dao.insertHabit(it.toEntity())
        }
    }

    private suspend fun handleAlarm(habit: Habit){
        try {
            val previous = dao.getHabitById(habit.id)
            alarmHandler.cancel(previous.toDomain())
        }catch (e: Exception){
            //El habito no existe
        }
        alarmHandler.setRecurringAlarm(habit)
    }


    override suspend fun getHabitById(id: String): Habit {
        return dao.getHabitById(id).toDomain()
    }
}