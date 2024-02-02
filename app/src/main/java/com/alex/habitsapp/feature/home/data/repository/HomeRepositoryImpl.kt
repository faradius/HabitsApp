package com.alex.habitsapp.feature.home.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.alex.habitsapp.feature.home.data.extension.toStartOfDateTimestamp
import com.alex.habitsapp.feature.home.data.local.HomeDao
import com.alex.habitsapp.feature.home.data.local.entity.HabitSyncEntity
import com.alex.habitsapp.feature.home.data.mapper.toDomain
import com.alex.habitsapp.feature.home.data.mapper.toDto
import com.alex.habitsapp.feature.home.data.mapper.toEntity
import com.alex.habitsapp.feature.home.data.mapper.toSyncEntity
import com.alex.habitsapp.feature.home.data.remote.HomeApi
import com.alex.habitsapp.feature.home.data.remote.util.resultOf
import com.alex.habitsapp.feature.home.data.sync.HabitSyncWorker
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
import java.time.Duration
import java.time.LocalTime
import java.time.ZonedDateTime

@RequiresApi(Build.VERSION_CODES.O)
class HomeRepositoryImpl(
    private val dao: HomeDao,
    private val api: HomeApi,
    private val alarmHandler: AlarmHandler,
    private val workmanager: WorkManager
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
        }.onFailure {
            dao.insertHabitSync(habit.toSyncEntity())
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

    override suspend fun syncHabits() {
        //El OneTimeWorkRequestBuilder se ejecuta una sola vez y esta worker periodico que se ejecua x minutos,
        //pero se va a ejecutar esete de una sola vez por es probable que el usuario se salga y vuelva a entrar
        //a la app y se ejecute el worker de nuevo
        //PeridicWorkRequestBuilder el work periodico
        //Workmanager verifica si tenemos internet o no, se ejecuta si no tenemos internet
        val worker = OneTimeWorkRequestBuilder<HabitSyncWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
             //EL BackoffPolicy exponential hace que se espere 5, 10, 15, 20, 25 minutos, cada vez que falle se autoincrementa el tiempo de reintentar
            //pero el backoffPolicy linear hace que se espere 5 minutos cada vez que falle para reintentar nuevamente
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5)).build()

        //El beginUniqueWork hace que se mantenga el workmanager cuando uno se sale de la aplicación y vuelva a entrar
        //hay otros que es el remplaza ExistingWorkPolicy.REPLACE, que se ejecute otro workmanager teniendo dos al mismo tiempo ExistingWorkPolicy.APPEND
        workmanager.beginUniqueWork("sync_habits_id", ExistingWorkPolicy.REPLACE, worker).enqueue()
    }
}